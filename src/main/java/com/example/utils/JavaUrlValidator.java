package com.example.utils;

import java.net.MalformedURLException;
import java.net.URL;

public class JavaUrlValidator {

	public static boolean isValidUrl(String originalUrl) {
		if (originalUrl == null || originalUrl.trim().isEmpty()) {
			return false;
		}

		try {
			URL urlObj = new URL(originalUrl);
			String protocol = urlObj.getProtocol();
			return "http".equals(protocol) || "https".equals(protocol);
		} catch (MalformedURLException e) {
			return false;
		}
	}
}
