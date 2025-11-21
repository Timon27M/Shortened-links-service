package com.example.service.url;

import com.example.model.UrlInfo;
import com.example.model.UserData;
import com.example.utils.ColorPrint;
import java.util.HashMap;

public class UrlValidationService {
    public static boolean isValidUrl(UrlInfo urlInfo) {
         if (!UrlExpirationTimeService.checkActiveUrlExpirationTime(urlInfo)) {
             ColorPrint.printlnRed("Время жизни ссылки истекло.");
             return false;
         }
         if (!UrlLimitService.checkLimitUrl(urlInfo)) {
             ColorPrint.printlnRed("Лимит переходов закончичлся");
             return false;
         }

         return true;
    }

    public static boolean checkOriginalUrlExists(String originalUrl, HashMap<String, UrlInfo> urls) {
        for (UrlInfo urlInfo : urls.values()) {
            if (urlInfo.getOriginalUrl().equals(originalUrl)) {
                return true;
            }
        }

        return false;
    }

}
