package com.example.utils;

import java.util.Base64;

public class CustomUrlShortener {
	private static final String BASE_URL = "clck.ru/";

	public static String shortenUrl(String originalUrl, String uuid) {
		String shortCode = Base64.getUrlEncoder().withoutPadding().encodeToString(uuid.getBytes()).substring(0, 8);

		return BASE_URL + shortCode;
	}

	public static boolean isValidShortUrl(String shortUrl) {
		return shortUrl != null && shortUrl.startsWith(BASE_URL);
	}
}
