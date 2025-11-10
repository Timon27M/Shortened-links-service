package com.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UrlInfo {
    private int limit;
    private String originalUrl;

    @JsonCreator
    public UrlInfo(@JsonProperty("originUrl") String originalUrl,  @JsonProperty("limit") int limit) {
        this.limit = limit;
        this.originalUrl = originalUrl;
    }

    public UrlInfo(String originalUrl) {
        this.limit = 5;
        this.originalUrl = originalUrl;
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
