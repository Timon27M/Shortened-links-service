package com.example.repository;

import com.example.model.UrlInfo;
import com.example.utils.HashMapJsonConverter;
import java.util.HashMap;

public class UrlRepository {
    private final String filePath = "UrlsDB.json";
    private HashMap<String, UrlInfo> urls;

    public UrlRepository() {
        this.urls = HashMapJsonConverter.loadHashMapFromJson(filePath, String.class, UrlInfo.class);
    }

    public UrlInfo getUrlInfo(String shortUrl) {
        return urls.get(shortUrl);
    }

    public HashMap<String, UrlInfo> getUrls() {
        return urls;
    }

    public void saveUrl(String shortUrl, UrlInfo urlInfo) {
        urls.put(shortUrl, urlInfo);
        saveUrls();
    }

    public void saveUrls() {
        HashMapJsonConverter.saveHashMapToJson(urls, filePath);
    }

    public boolean checkUrl(String shortUrl) {
        return urls.containsKey(shortUrl);
    }
}
