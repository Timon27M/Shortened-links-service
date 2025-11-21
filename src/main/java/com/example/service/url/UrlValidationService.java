package com.example.service.url;

import com.example.utils.JavaUrlValidator;

public class UrlValidationService {

    public static void validate(String url) {
        if (!JavaUrlValidator.isValidUrl(url)) {
            throw new IllegalArgumentException("Невалидная ссылка");
        }
    }
}
