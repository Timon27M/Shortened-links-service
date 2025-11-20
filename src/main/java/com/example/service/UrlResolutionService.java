package com.example.service;

import com.example.model.UrlInfo;
import com.example.model.UserData;
import com.example.repository.UserRepository;
import com.example.utils.ColorPrint;
import java.util.HashMap;
import java.util.Map;

public class UrlResolutionService {
    private final UserRepository userRepository;
    private final UrlDataService urlDataService;
    private final UrlValidationService urlValidationService;
    private final UrlCleanupService urlCleanupService;

    public UrlResolutionService(UserRepository userRepository,
                                UrlDataService urlDataService,
                                UrlValidationService urlValidationService,
                                UrlCleanupService urlCleanupService) {
        this.userRepository = userRepository;
        this.urlDataService = urlDataService;
        this.urlValidationService = urlValidationService;
        this.urlCleanupService = urlCleanupService;
    }

    public String getOriginalUrl(String shortUrl) {
        if (Boolean.FALSE.equals(urlValidationService.isUrlActive(shortUrl))) {
            urlCleanupService.cleanupUrl(shortUrl);
        }

        String originalUrl = urlDataService.getOriginalUrl(shortUrl);
        if (originalUrl != null) {
            return handleFoundUrl(shortUrl, originalUrl);
        }

        return findUrlInUserData(shortUrl);
    }

    private String handleFoundUrl(String shortUrl, String originalUrl) {
        if (Boolean.FALSE.equals(urlValidationService.isLimitReached(shortUrl))) {
            urlCleanupService.cleanupUrl(shortUrl);
            return null;
        }
        return originalUrl;
    }

    private String findUrlInUserData(String shortUrl) {
        for (Map.Entry<String, UserData> userEntry : userRepository.getUsers().entrySet()) {
            String login = userEntry.getKey();
            UserData userData = userEntry.getValue();
            HashMap<String, UrlInfo> urls = userData.getUrls();

            if (urls.containsKey(shortUrl)) {
                return handleUserUrl(login, shortUrl, urls.get(shortUrl));
            }
        }
        return null;
    }

    private String handleUserUrl(String login, String shortUrl, UrlInfo urlInfo) {
        if (urlInfo == null) return null;

        // Проверяем активность и лимиты
        if (!urlCleanupService.isUserUrlActive(login, shortUrl)) {
            urlCleanupService.cleanupUrl(shortUrl);
            return null;
        }

        if (Boolean.FALSE.equals(urlCleanupService.checkUserUrlLimit(login, shortUrl))) {
            ColorPrint.printlnRed("Лимит переходов закончился, ссылка удалена.");
            urlCleanupService.cleanupUserUrl(login, shortUrl);
            return null;
        }

        return urlInfo.getOriginalUrl();
    }
}
