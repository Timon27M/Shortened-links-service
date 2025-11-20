package com.example.service;

import com.example.model.UrlInfo;
import com.example.repository.UrlRepository;
import com.example.utils.ColorPrint;

public class UrlDataService {
  private final UrlRepository urlRepository;

  public UrlDataService(UrlRepository urlRepository) {
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

    return urlData != null ? urlData.getOriginalUrl() : null;
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

  public void deleteUrl(String shortUrl) {
    urlRepository.deleteUrl(shortUrl);
  }

  public void decrementLimit(String shortUrl) {}
}
