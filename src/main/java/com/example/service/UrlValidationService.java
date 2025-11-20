package com.example.service;

import com.example.model.UrlInfo;
import com.example.model.UserData;
import com.example.repository.UrlRepository;
import com.example.repository.UserRepository;
import com.example.utils.UtilConstants;
import java.time.LocalDateTime;
import org.jetbrains.annotations.Nullable;

public class UrlValidationService {
  private final UrlRepository urlRepository;
  private final UserRepository userRepository;

  public UrlValidationService(UrlRepository urlRepository, UserRepository userRepository) {
    this.urlRepository = urlRepository;
    this.userRepository = userRepository;
  }

  public boolean isUrlExists(String shortUrl) {
    for (UserData user : userRepository.getUsers().values()) {}

    return urlRepository.checkUrl(shortUrl);
  }

  public @Nullable Boolean isLimitReached(String shortUrl) {
    UrlInfo urlInfo = getUrlInfo(shortUrl);
    if (urlInfo == null) {
      return null;
    }
    return urlInfo.getLimit() != 0;
  }

  public @Nullable Boolean isUrlActive(String shortUrl) {
    Integer expirationTimeUrl = urlRepository.getExpirationTimeUrl(shortUrl);
    if (expirationTimeUrl == null) return null;

    LocalDateTime dateUrl =
        LocalDateTime.parse(urlRepository.getDateUrl(shortUrl), UtilConstants.FORMATTER)
            .plusMinutes(expirationTimeUrl);

    return !dateUrl.isBefore(LocalDateTime.now());
  }

  private UrlInfo getUrlInfo(String shortUrl) {
    return urlRepository.getUrlInfo(shortUrl);
  }
}
