package com.example.service.url;

import com.example.model.UrlInfo;

public class UrlLimitService {
    public static boolean checkLimit(UrlInfo info) {
        return info.getLimit() > 0;
    }

    public static void decrement(UrlInfo info) {
        info.decrimentLimit();
    }
}
