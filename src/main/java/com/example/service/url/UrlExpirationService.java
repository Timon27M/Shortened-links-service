package com.example.service.url;

import com.example.model.UrlInfo;
import com.example.utils.UtilConstants;
import java.time.LocalDateTime;

public class UrlExpirationService {
    public static boolean isUrlActive(UrlInfo info) {
        LocalDateTime created = LocalDateTime.parse(info.getDate(), UtilConstants.FORMATTER);
        LocalDateTime expires = created.plusMinutes(info.getExpirationTime());

        return LocalDateTime.now().isBefore(expires);
    }
}
