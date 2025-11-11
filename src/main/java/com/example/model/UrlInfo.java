package com.example.model;

import org.jetbrains.annotations.Nullable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UrlInfo {
  private int limit;
  private String originalUrl;
  private String date;
  private int expirationTime;
  private DateTimeFormatter formatter;

    public UrlInfo() {}

  public UrlInfo(String originalUrl, @Nullable Integer limit, @Nullable Integer expirationTime) {
    this.originalUrl = originalUrl;
    this.limit = limit == null ? 5 : limit;
    this.expirationTime = expirationTime == null ? 10 : expirationTime;

    LocalDateTime dateTime = LocalDateTime.now();
    this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    this.date = dateTime.format(formatter);
  }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getExpirationTime() {
        return expirationTime;
    }

    public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  public String getOriginalUrl() {
    return originalUrl;
  }

  public void setOriginalUrl(String originalUrl) {
    this.originalUrl = originalUrl;
  }
}
