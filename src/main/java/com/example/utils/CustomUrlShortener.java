package com.example.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.UUID;

public class CustomUrlShortener {
  private static final String BASE_URL = "clck.ru/";

  public static String shortenUrl(String originalUrl, UUID uuid) {
    try {
      String combined = uuid.toString() + "||" + originalUrl;

      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] hashBytes = md.digest(combined.getBytes(StandardCharsets.UTF_8));

      String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(hashBytes);
      return BASE_URL + encoded.substring(0, 8);

    } catch (Exception e) {
      return fallbackShortenUrl(originalUrl, uuid);
    }
  }

  public static String fallbackShortenUrl(String originalUrl, UUID uuid) {
    String combined = uuid.toString() + originalUrl;

    String shortCode =
        Base64.getUrlEncoder().withoutPadding().encodeToString(combined.getBytes()).substring(0, 8);

    return BASE_URL + shortCode;
  }
}
