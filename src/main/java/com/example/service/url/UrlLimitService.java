package com.example.service.url;

import com.example.model.UrlInfo;
import java.util.HashMap;

public class UrlLimitService {

	public static boolean decrementLimit(String shortUrl, HashMap<String, UrlInfo> urls) {
		if (urls.containsKey(shortUrl)) {
			UrlInfo urlInfo = urls.get(shortUrl);
			urlInfo.decrimentLimit();
			return true;
		}
		return false;
	}

	public static Boolean checkLimitUrl(UrlInfo urlInfo) {
		if (urlInfo == null) {
			return false;
		}
		return urlInfo.getLimit() != 0;
	}
}
