package com.example.model;

import com.example.utils.ColorPrint;
import com.example.utils.CustomUrlShortener;
import java.util.HashMap;
import java.util.UUID;

public class UserData {
	private String id;
	private HashMap<String, UrlInfo> urls;

	public UserData() {
		this.id = UUID.randomUUID().toString();
		this.urls = new HashMap<>();
	}

	public String addUrl(String originalUrl, Integer limit, Integer expirationTime) {
		if (checkUserOriginUrl(originalUrl)) {
			ColorPrint.printlnRed("Данный url уже зарегистрирован");
			return null;
		}

		String shortUrl = CustomUrlShortener.shortenUrl(originalUrl);
		UrlInfo urlInfo = new UrlInfo(originalUrl, limit, expirationTime);
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

	public boolean checkUserOriginUrl(String originlalUrl) {
		return this.urls.values().stream().anyMatch(urlInfo -> originlalUrl.equals(urlInfo.getOriginalUrl()));
	}

	public void deleteUrl(String shortUrl) {
		this.urls.remove(shortUrl);
	}
}
