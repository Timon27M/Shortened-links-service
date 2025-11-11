// package com.example.utils;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.Random;
//
// public class CustomUrlShortener {
//    private static Map<String, String> urlMap = new HashMap<>();
//    private static final String BASE_URL = "clck.ru/";
//    private static final String CHARACTERS =
// "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//    private static final int SHORT_CODE_LENGTH = 6;
//
//    public static String shortenUrl(String originUrl) {
//        String shortCode = generateShortCode();
//        urlMap.put(shortCode, originUrl);
//        return BASE_URL + shortCode;
//    }
//
//    private static String generateShortCode() {
//        Random random = new Random();
//        StringBuilder sb = new StringBuilder();
//
//        for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
//            int index = random.nextInt(CHARACTERS.length());
//            sb.append(CHARACTERS.charAt(index));
//        }
//
//        // Проверяем уникальность кода
//        if (urlMap.containsKey(sb.toString())) {
//            return generateShortCode();
//        }
//
//        return sb.toString();
//    }
// }

package com.example.utils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomUrlShortener {
    private static final String BASE_URL = "clck.ru/";

    // Только генерация короткого URL, без хранения
    public static String shortenUrl(String originalUrl) {
        String uuid = UUID.randomUUID().toString();
        String shortCode = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(uuid.getBytes())
                .substring(0, 8);

        return BASE_URL + shortCode;
    }

    // Дополнительный метод для проверки формата
    public static boolean isValidShortUrl(String shortUrl) {
        return shortUrl != null && shortUrl.startsWith(BASE_URL);
    }
}
