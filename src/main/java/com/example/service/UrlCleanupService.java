package com.example.service;

import com.example.model.UrlInfo;
import com.example.repository.UrlRepository;
import com.example.repository.UserRepository;
import com.example.utils.ColorPrint;
import com.example.utils.UtilConstants;
import java.time.LocalDateTime;
import org.jetbrains.annotations.Nullable;

public class UrlCleanupService {
  private final UserRepository userRepository;
  private final UrlDataService urlDataService;
  private final UrlValidationService urlValidationService;
  private final UrlRepository urlRepository;

  public UrlCleanupService(
      UserRepository userRepository,
      UrlDataService urlDataService,
      UrlValidationService urlValidationService,
      UrlRepository urlRepository) {
    this.userRepository = userRepository;
    this.urlDataService = urlDataService;
    this.urlValidationService = urlValidationService;
    this.urlRepository = urlRepository;
  }

  public void cleanupUrl(String shortUrl) {
    urlDataService.deleteUrl(shortUrl);
    deleteUrlFromAllUsers(shortUrl);
  }

  public boolean isUserUrlActive(String login, String shortUrl) {
    // используем UserRepository напрямую
    UrlInfo urlInfo = getUserUrlInfo(login, shortUrl);
    if (urlInfo == null) return false;

    LocalDateTime expirationDate =
        LocalDateTime.parse(urlInfo.getDate(), UtilConstants.FORMATTER)
            .plusMinutes(urlInfo.getExpirationTime());
    return !expirationDate.isBefore(LocalDateTime.now());
  }

  private void deleteUrlFromAllUsers(String shortUrl) {
    userRepository
        .getUsers()
        .forEach(
            (login, userData) -> {
              if (userData.getUrls().containsKey(shortUrl)) {
                userRepository.deleteUrlToUser(login, shortUrl);
              }
            });
  }

  private UrlInfo getUserUrlInfo(String login, String shortUrl) {
    if (!userRepository.checkUser(login)) {
      ColorPrint.printlnRed("Пользователь не найден");
      return null;
    }
    return userRepository.findUser(login).getShortUrlData(shortUrl);
  }

  public @Nullable Boolean checkUserUrlLimit(String login, String shortUrl) {
    UrlInfo urlInfo = getUserUrlInfo(login, shortUrl);
    return urlInfo != null ? urlInfo.getLimit() != 0 : null;
  }

  public void cleanupUserUrl(String login, String shortUrl) {
    userRepository.deleteUrlToUser(login, shortUrl);
    urlRepository.deleteUrl(shortUrl);
  }
}
