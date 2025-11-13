package com.example.service;

import com.example.model.UrlInfo;
import com.example.repository.UrlRepository;
import com.example.utils.ColorPrint;
import com.example.utils.UtilConstants;
import java.time.LocalDateTime;
import org.jetbrains.annotations.Nullable;

public class UrlService {
  private final UrlRepository urlRepository;

  public UrlService(UrlRepository urlRepository) {
    this.urlRepository = urlRepository;
  }

  public String getOriginalUrl(String shortUrl) {
    if (shortUrl == null || shortUrl.isEmpty()) {
        ColorPrint.printlnRed("Ссылки не существует");
    }

    if (!urlRepository.checkUrl(shortUrl)) {
        return null;
    }

    UrlInfo urlData = urlRepository.getUrlInfo(shortUrl);

    if (urlData == null) {
        ColorPrint.printlnRed("Ссылка не найден");
    }

    return urlData.getOriginalUrl();
  }

  public UrlInfo getUrlData(String shortUrl) {
    UrlInfo urlData = urlRepository.getUrlInfo(shortUrl);

    if (urlData == null) {
      ColorPrint.printlnRed("Ссылка не найден");
    }

    return urlData;
  }

  public void saveUrl(String shortUrl, UrlInfo urlInfoMap) {
    urlRepository.saveUrl(shortUrl, urlInfoMap);
  }

  public void decrimentLimitUrlData(String shortUrl) {
    UrlInfo urlData = getUrlData(shortUrl);

    urlData.decrimentLimit();

    saveUrl(shortUrl, urlData);
  }

  public boolean checkUrl(String shortUrl) {
    return urlRepository.checkUrl(shortUrl);
  }

  public @Nullable Boolean checkLimitUrl(String shortUrl) {
    UrlInfo urlInfo = getUrlData(shortUrl);
    if (urlInfo == null) {
      return null;
    }
    return urlInfo.getLimit() != 0;
  }

  public void deleteUrl(String shortUrl) {
    urlRepository.deleteUrl(shortUrl);
  }

  public @Nullable Boolean checkActiveUrl(String shortUrl) {
    @Nullable Integer expirationTimeUrl = urlRepository.getExpirationTimeUrl(shortUrl);

    if (expirationTimeUrl == null) {
      return null;
    }
    LocalDateTime dateUrl =
        LocalDateTime.parse(urlRepository.getDateUrl(shortUrl), UtilConstants.FORMATTER)
            .plusMinutes(expirationTimeUrl);

    return !dateUrl.isBefore(LocalDateTime.now());
  }
}
