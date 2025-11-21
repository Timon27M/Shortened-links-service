package com.example.service.url;

import com.example.model.UrlInfo;
import com.example.utils.CustomUrlShortener;
import java.util.HashMap;

public class UrlService {
    public static String createShortUrl(String originalUrl, String id) {
        return CustomUrlShortener.shortenUrl(originalUrl, id);
    }

    public static UrlInfo createUrlInfo(String originalUrl, Integer limit, Integer expirationTime) {
        UrlInfo urlInfo = new UrlInfo(originalUrl, limit, expirationTime);

        return urlInfo;
    }

    public static boolean deleteUrl(String shortUrl, HashMap<String, UrlInfo> urls) {
        if (urls.containsKey(shortUrl)) {
            urls.remove(shortUrl);
            return true;
        }

        return false;
    }
}
