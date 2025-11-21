package com.example.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.jetbrains.annotations.Nullable;

public class UrlInfo {
	private int limit;
	private String originalUrl;
	private String date;
	private int expirationTime;
	private DateTimeFormatter formatter;

	public UrlInfo() {
	}

	public UrlInfo(String originalUrl, @Nullable Integer limit, @Nullable Integer expirationTime) {
		this.originalUrl = originalUrl;
		this.limit = limit == null ? 5 : limit;
		this.expirationTime = expirationTime == null ? 10 : expirationTime;

		this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		this.date = LocalDateTime.now().format(formatter);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public @Nullable Integer getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(int expirationTime) {
		this.expirationTime = expirationTime;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getOriginalUrl() {
		return this.originalUrl;
	}

	public void decrimentLimit() {
		this.limit = this.limit - 1;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}
}
