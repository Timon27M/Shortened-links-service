package com.example.service.url;

import com.example.model.UrlInfo;
import com.example.utils.CustomUrlShortener;
import java.util.HashMap;
import java.util.UUID;

public class UrlService {
  public String createShortUrl(String originalUrl, UUID id) {
    return CustomUrlShortener.shortenUrl(originalUrl, id);
  }

  public UrlInfo createUrlInfo(String originalUrl, Integer limit, Integer expirationTime) {
    UrlInfo urlInfo = new UrlInfo(originalUrl, limit, expirationTime);

    return urlInfo;
  }

  public boolean deleteUrl(String shortUrl, HashMap<String, UrlInfo> urls) {
    if (urls.containsKey(shortUrl)) {
      urls.remove(shortUrl);
      return true;
    }

    return false;
  }

  public boolean isExistsOriginalUrl(String originalUrl, HashMap<String, UrlInfo> urls) {
    for (UrlInfo urlInfo : urls.values()) {
      if (originalUrl.equals(urlInfo.getOriginalUrl())) {
        return true;
      }
    }

    return false;
  }
}
