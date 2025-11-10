//package com.example.utils;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//public class CustomUrlShortener {
//    private static Map<String, String> urlMap = new HashMap<>();
//    private static final String BASE_URL = "clck.ru/";
//    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
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
//}

package com.example.utils;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomUrlShortener {
    private static final String BASE_URL = "clck.ru/";

    // Храним: UUID → оригинальная ссылка
    private static Map<String, String> uuidToUrlMap = new HashMap<>();

    public static String shortenUrl(String originUrl) {
        String uuid = UUID.randomUUID().toString();

        // Сохраняем связь
        uuidToUrlMap.put(uuid, originUrl);

        // Создаем короткий код из UUID
        String shortCode = generateShortCodeFromUuid(uuid);

        return BASE_URL + shortCode;
    }

    // Дешифровка с проверкой соответствия UUID и shortUrl
    public static String getOriginalUrl(String uuid, String shortUrl) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        }
        if (shortUrl == null || !shortUrl.startsWith(BASE_URL)) {
            throw new IllegalArgumentException("Invalid short URL format");
        }

        // Проверяем, что shortUrl соответствует UUID
        String expectedShortCode = generateShortCodeFromUuid(uuid);
        String actualShortCode = shortUrl.replace(BASE_URL, "");

        if (!expectedShortCode.equals(actualShortCode)) {
            throw new IllegalArgumentException("UUID and short URL do not match");
        }

        // Возвращаем оригинальную ссылку
        String originalUrl = uuidToUrlMap.get(uuid);
        if (originalUrl == null) {
            throw new IllegalArgumentException("No URL found for UUID: " + uuid);
        }

        return originalUrl;
    }

    private static String generateShortCodeFromUuid(String uuid) {
        return Base64.getUrlEncoder().withoutPadding()
                .encodeToString(uuid.getBytes())
                .substring(0, 8);
    }
}
