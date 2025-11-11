package com.example.model;

import com.example.utils.CustomUrlShortener;
import com.example.utils.JavaUrlValidator;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.UUID;

public class UserData {
    private String id;
    private HashMap<String, UrlInfo> urls;

    public UserData() {
        this.id = UUID.randomUUID().toString();
        this.urls = new HashMap<>();
    }

    @JsonCreator
    public UserData(
            @JsonProperty("id") String id,
            @JsonProperty("urls") HashMap<String, UrlInfo> urls) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.urls = urls != null ? urls : new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public HashMap<String, UrlInfo> getUrls() {
        return urls;
    }

    public void addUserUrl(String originUrl, int limit) {
        if (checkUserOriginUrl(originUrl)) {
            System.out.println("Данный url уже зарегистрирован");
            return;
        }

        String shortUrl = CustomUrlShortener.shortenUrl(originUrl);

        UrlInfo urlData = new UrlInfo(originUrl, limit);

        this.urls.put(shortUrl, urlData);
    }

    public UrlInfo getShortUrlData(String shortUrl) {
        return this.urls.get(shortUrl);
    }

    public boolean checkUserShortUrl(String shortUrl) {
        return this.urls.containsKey(shortUrl);
    }

    public boolean checkUserOriginUrl(String originUrl) {
       return this.urls.values().stream().anyMatch(urlInfo -> originUrl.equals(urlInfo.getOriginalUrl()));
    }

}
