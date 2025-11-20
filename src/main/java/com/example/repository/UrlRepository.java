//package com.example.repository;
//
//import com.example.model.UrlInfo;
//import com.example.utils.HashMapJsonConverter;
//import java.util.HashMap;
//import org.jetbrains.annotations.Nullable;
//
//public class UrlRepository {
//  private final String filePath = "";
//  private HashMap<String, UrlInfo> urls;
//
//  public UrlRepository() {
//    this.urls = HashMapJsonConverter.loadHashMapFromJson(filePath, String.class, UrlInfo.class);
//  }
//
//  public UrlInfo getUrlInfo(String shortUrl) {
//    return urls.get(shortUrl);
//  }
//
//  public String getDateUrl(String shortUrl) {
//    return urls.get(shortUrl).getDate();
//  }
//
//  public @Nullable Integer getExpirationTimeUrl(String shortUrl) {
//    UrlInfo urlInfo = getUrlInfo(shortUrl);
//    if (urlInfo == null) {
//      return null;
//    }
//    return urlInfo.getExpirationTime();
//  }
//
//  public HashMap<String, UrlInfo> getUrls() {
//    return urls;
//  }
//
//  public void saveUrl(String shortUrl, UrlInfo urlInfo) {
//    urls.put(shortUrl, urlInfo);
//    saveUrls();
//  }
//
//  public void saveUrls() {
//    HashMapJsonConverter.saveHashMapToJson(urls, filePath);
//  }
//
//  public boolean checkUrl(String shortUrl) {
//    return urls.containsKey(shortUrl);
//  }
//
//  public void deleteUrl(String shortUrl) {
//    urls.remove(shortUrl);
//    saveUrls();
//  }
//}
