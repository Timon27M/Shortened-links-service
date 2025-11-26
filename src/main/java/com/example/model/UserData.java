package com.example.model;

import java.util.HashMap;
import java.util.UUID;

public class UserData {
	private String id;
	private HashMap<String, UrlInfo> urls;

	public UserData() {
		this.id = UUID.randomUUID().toString();
		this.urls = new HashMap<>();
	}

	public String addUrl(String shortUrl, UrlInfo urlInfo) {
		this.urls.put(shortUrl, urlInfo);

		return shortUrl;
	}

	public String getId() {
		return id;
	}

	public HashMap<String, UrlInfo> getUrls() {
		return urls;
	}

	public UrlInfo getShortUrlData(String shortUrl) {
		return this.urls.get(shortUrl);
	}

	public boolean checkUserShortUrl(String shortUrl) {
		return this.urls.containsKey(shortUrl);
	}

	public void deleteUrl(String shortUrl) {
		this.urls.remove(shortUrl);
	}
}
