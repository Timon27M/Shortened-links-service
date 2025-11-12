package com.example.service;

import com.example.model.UrlInfo;
import com.example.repository.UrlRepository;

public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String getOriginalUrl(String shortUrl) {
        if (shortUrl == null || shortUrl.isEmpty()) {
            throw new IllegalArgumentException("Ссылки не существует");
        }

        if (!urlRepository.checkUrl(shortUrl)) {
            return null;
        }

       UrlInfo urlData = urlRepository.getUrlInfo(shortUrl);

       if (urlData == null) {
           throw new IllegalArgumentException("Ссылка не найден");
       }

       return urlData.getOriginalUrl();
    }

    public void saveUrl(String shortUrl, UrlInfo urlInfoMap) {
        urlRepository.saveUrl(shortUrl, urlInfoMap);
    }
}
