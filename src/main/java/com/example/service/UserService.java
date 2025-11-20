package com.example.service;

import com.example.model.UrlInfo;
import com.example.model.UserData;
import com.example.repository.UserRepository;
import com.example.utils.ColorPrint;
import com.example.utils.JavaUrlValidator;
import com.example.utils.UtilConstants;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void createUser(String login, String originalUrl, Integer limit, Integer expirationTime) {
    if (!JavaUrlValidator.isValidUrl(originalUrl)) {
      throw new IllegalArgumentException("Невалидная ссылка");
    }

    UserData user = new UserData();
    String shortUrl = user.addUrl(originalUrl, limit, expirationTime);

    userRepository.saveUser(login, user);
    ColorPrint.printlnGreen("Создана короткая ссылка: " + shortUrl);
  }

  public void addUrlToUser(
      String login, String originalUrl, Integer limit, Integer expirationTime) {
    if (!JavaUrlValidator.isValidUrl(originalUrl)) {
      throw new IllegalArgumentException("Невалидная ссылка");
    }

    if (!userRepository.checkUser(login)) {
      throw new IllegalArgumentException("Пользователь не найден");
    }

    UserData user = userRepository.findUser(login);

    String shortUrl = user.addUrl(originalUrl, limit, expirationTime);
    if (shortUrl == null) {
      return;
    }
    userRepository.saveUser(login, user);
    ColorPrint.printlnGreen("Создана короткая ссылка: " + shortUrl);
  }

  public UrlInfo getUrlInfo(String login, String shortUrl) {
    if (!userRepository.checkUser(login)) {
      ColorPrint.printlnRed("Пользователь не найден");
      return null;
    }

    return userRepository.findUser(login).getShortUrlData(shortUrl);
  }

  public String getOriginalUrl(String shortUrl) {
      for (Map.Entry<String, UserData> user : userRepository.getUsers().entrySet()) {
        String login = user.getKey();
        UserData userData = user.getValue();

        HashMap<String, UrlInfo> urls = userData.getUrls();

        if (urls.containsKey(shortUrl)) {
          if (!checkActiveUserUrl(login, shortUrl)) {
            deleteUrlToUser(shortUrl);
          }
          if (checkLimitUrl(login, shortUrl) == Boolean.FALSE) {
            ColorPrint.printlnRed("Лимит переходов закончичлся, ссылка удалена.");
            deleteUrlToUser(shortUrl);
            return null;
          }
          UrlInfo urlInfo = urls.get(shortUrl);
          if (urlInfo == null) {
            return null;
          }
          return urlInfo.getOriginalUrl();
        }
    }
      return null;
  }

  public void decrementLimit(String shortUrl) {
    for (Map.Entry<String, UserData> user : userRepository.getUsers().entrySet()) {
      HashMap<String, UrlInfo> urls = user.getValue().getUrls();

      if (urls.containsKey(shortUrl)) {
        UrlInfo urlInfo = urls.get(shortUrl);
        urlInfo.decrimentLimit();
        userRepository.saveUser(user.getKey(), user.getValue());
        break;
      }
    }
  }

  public @Nullable Boolean checkLimitUrl(String login, String shortUrl) {
    UrlInfo urlInfo = getUrlInfo(login, shortUrl);
    if (urlInfo == null) {
      return null;
    }
    return urlInfo.getLimit() != 0;
  }

  private void deleteUrlToUser(String shortUrl) {
    userRepository
        .getUsers()
        .forEach(
            (login, data) -> {
              HashMap<String, UrlInfo> urls = data.getUrls();
              if (urls.containsKey(shortUrl)) {
                userRepository.deleteUrlToUser(login, shortUrl);
              }
            });
  }

  public boolean checkActiveUserUrl(String login, String shortUrl) {
    UrlInfo urlInfo = getUrlInfo(login, shortUrl);

    Integer expirationTimeUserUrl = urlInfo.getExpirationTime();

    LocalDateTime dateUserUrl =
        LocalDateTime.parse(urlInfo.getDate(), UtilConstants.FORMATTER)
            .plusMinutes(expirationTimeUserUrl);
    return !dateUserUrl.isBefore(LocalDateTime.now());
  }

  public boolean checkUser(String login) {
    return userRepository.checkUser(login);
  }
}
