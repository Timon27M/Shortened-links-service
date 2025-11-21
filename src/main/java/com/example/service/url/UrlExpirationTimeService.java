package com.example.service.url;

import com.example.model.UrlInfo;
import com.example.utils.UtilConstants;
import java.time.LocalDateTime;

public class UrlExpirationTimeService {

    public static boolean checkActiveUrlExpirationTime(UrlInfo urlInfo) {
        Integer expirationTimeUserUrl = urlInfo.getExpirationTime();
        LocalDateTime dateUserUrl = LocalDateTime.parse(urlInfo.getDate(), UtilConstants.FORMATTER)
                .plusMinutes(expirationTimeUserUrl);

        return !dateUserUrl.isBefore(LocalDateTime.now());
    }
}
