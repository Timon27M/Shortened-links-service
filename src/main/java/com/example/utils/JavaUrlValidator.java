package com.example.utils;

import java.net.MalformedURLException;
import java.net.URL;

public class JavaUrlValidator {

    public static boolean isValidUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }

        try {
            URL urlObj = new URL(url);
            String protocol = urlObj.getProtocol();
            return "http".equals(protocol) || "https".equals(protocol);
        } catch (MalformedURLException e) {
            return false;
        }
    }
}

