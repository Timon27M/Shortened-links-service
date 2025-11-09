package com.example.utils;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CustomUrlShortener {
    private static Map<String, String> urlMap = new HashMap<>();
    private static final String BASE_URL = "clck.ru/";
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_CODE_LENGTH = 6;

    public static String shortenUrl(String originUrl) {
        String shortCode = generateShortCode();
        urlMap.put(shortCode, originUrl);
        return BASE_URL + shortCode;
    }

    private static String generateShortCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        // Проверяем уникальность кода
        if (urlMap.containsKey(sb.toString())) {
            return generateShortCode();
        }

        return sb.toString();
    }
}

//package com.example.utils;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//public class CustomUrlShortener {
//    private static final String BASE_URL = "clck.ru/";
//
//    // Храним: UUID → оригинальная ссылка
//    private static Map<String, String> uuidToUrlMap = new HashMap<>();
//
//    public static String shortenUrl(String originUrl) {
//        String uuid = UUID.randomUUID().toString();
//
//        // Сохраняем связь
//        uuidToUrlMap.put(uuid, originUrl);
//
//        // Создаем короткий код из UUID
//        String shortCode = Base64.getUrlEncoder().withoutPadding()
//                .encodeToString(uuid.getBytes())
//                .substring(0, 8);
//
//        return BASE_URL + shortCode;
//    }
//
//    public static String getOriginalUrl(String uuid) {
//        // Прямая дешифровка по UUID
//        return uuidToUrlMap.get(uuid);
//    }
//
//    public static String getUuidFromShortUrl(String shortUrl, String uuid) {
//        String shortCode = shortUrl.replace(BASE_URL, "");
//
//        // В реальном приложении нужно хранить соответствие shortCode → UUID
//        // Для демонстрации ищем по значению
//            String testShortCode = Base64.getUrlEncoder().withoutPadding()
//                    .encodeToString(uuid.getBytes())
//                    .substring(0, 8);
//            if (testShortCode.equals(shortCode)) {
//                return uuid;
//            }
//        return null;
//    }
//}