package com.example.service.url;

import com.example.model.UrlInfo;
import com.example.utils.ColorPrint;

public class UrlValidationService {
	public static boolean isValidUrl(UrlInfo urlInfo) {
		if (!UrlExpirationTimeService.checkActiveUrlExpirationTime(urlInfo)) {
			ColorPrint.printlnRed("Время жизни ссылки истекло, ссылка удалена.");
			return false;
		}
		if (!UrlLimitService.checkLimitUrl(urlInfo)) {
			ColorPrint.printlnRed("Лимит переходов закончичлся, ссылка удалена.");
			return false;
		}

		return true;
	}
}
