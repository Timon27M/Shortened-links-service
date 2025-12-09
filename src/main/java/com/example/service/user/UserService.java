package com.example.service.user;

import com.example.model.UrlInfo;
import com.example.model.UserData;
import com.example.repository.UserRepository;
import com.example.service.url.UrlExpirationTimeService;
import com.example.service.url.UrlLimitService;
import com.example.service.url.UrlService;
import com.example.service.url.UrlValidationService;
import com.example.utils.ColorPrint;
import com.example.utils.JavaUrlValidator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserService {
  private final UserRepository userRepository;
  private final UrlService urlService;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
    this.urlService = new UrlService();
  }

  private String createShortUrlUser(
      String originalUrl, Integer limit, Integer expirationTime, UserData user) {
    String shortUrl = urlService.createShortUrl(originalUrl, user.getId());
    UrlInfo urlInfo = urlService.createUrlInfo(originalUrl, limit, expirationTime);
    user.addUrl(shortUrl, urlInfo);

    return shortUrl;
  }

  public void createUser(String login, String originalUrl, Integer limit, Integer expirationTime) {
    if (!JavaUrlValidator.isValidUrl(originalUrl)) {
      throw new IllegalArgumentException("Невалидная ссылка");
    }

    if (checkUser(login)) {
      ColorPrint.printlnRed("Пользователь существует");
      return;
    }

    UserData user = new UserData();
    String shortUrl = createShortUrlUser(originalUrl, limit, expirationTime, user);

    userRepository.saveUser(login, user);
    ColorPrint.printlnGreen("Ваш уникальный UUID: " + user.getId());
    ColorPrint.printlnGreen("Создана короткая ссылка: " + shortUrl);
  }

  public void addUrlToUser(
      String login, String originalUrl, Integer limit, Integer expirationTime) {
    if (!JavaUrlValidator.isValidUrl(originalUrl)) {
      ColorPrint.printlnRed("Невалидная ссылка");
      return;
    }

    if (!checkUser(login)) {
      ColorPrint.printlnRed("Пользователь не найден");
      return;
    }

    UserData user = userRepository.findUser(login);

    String shortUrl = createShortUrlUser(originalUrl, limit, expirationTime, user);
    if (shortUrl == null) {
      return;
    }
    userRepository.saveUser(login, user);
    ColorPrint.printlnGreen("Создана короткая ссылка: " + shortUrl);
  }

  public String getOriginalUrl(String shortUrl) {
    for (Map.Entry<String, UserData> user : userRepository.getUsers().entrySet()) {
      String login = user.getKey();
      UserData userData = user.getValue();
      UrlInfo urlInfo = userData.getShortUrlData(shortUrl);

      if (urlInfo != null) {
        if (!UrlValidationService.isValidUrl(urlInfo)) {
          if (!UrlExpirationTimeService.checkActiveUrlExpirationTime(urlInfo)) {
            deleteUrlToUser(login, shortUrl);
          }
          break;
        }
        return urlInfo.getOriginalUrl();
      }
    }
    return null;
  }

  public void decrementLimitUrl(String shortUrl) {
    for (Map.Entry<String, UserData> user : userRepository.getUsers().entrySet()) {
      HashMap<String, UrlInfo> urls = user.getValue().getUrls();

      if (UrlLimitService.decrementLimit(shortUrl, urls)) {
        userRepository.saveUser(user.getKey(), user.getValue());
        break;
      }
    }
  }

  private void deleteUrlToUser(String login, String shortUrl) {
    UserData data = userRepository.findUser(login);
    HashMap<String, UrlInfo> urls = data.getUrls();
    if (urlService.deleteUrl(shortUrl, urls)) {
      userRepository.saveUser(login, data);
    }
  }

  public boolean checkOriginalUrlByUser(String login, String originalUrl) {
    HashMap<String, UrlInfo> urls = userRepository.findUser(login).getUrls();

    return urlService.isExistsOriginalUrl(originalUrl, urls);
  }

  public boolean checkUser(String login) {
    return userRepository.checkUser(login);
  }

  public boolean checkUserUUID(String login, UUID uuid) {
    UserData user = userRepository.findUser(login);
    return user.getId().equals(uuid);
  }
}
