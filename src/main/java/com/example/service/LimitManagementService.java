package com.example.service;

import com.example.repository.UserRepository;

public class LimitManagementService {
    private final UserRepository userRepository;
    private final UrlDataService urlDataService;
    private final UrlValidationService urlValidationService;

    public LimitManagementService(UserRepository userRepository, UrlDataService urlDataService, UrlValidationService urlValidationService) {
        this.userRepository = userRepository;
        this.urlDataService = urlDataService;
        this.urlValidationService = urlValidationService;
    }

    public void decrementLimit(String shortUrl) {
        if (urlValidationService.isUrlExists(shortUrl)) {
            urlDataService.decrementLimit(shortUrl);
            return;
        }

        decrementLimitInUserData();
    }

    public void decrementLimitInUserData(String shortUrl) {
        if (urlValidationService.isUrlExists(shortUrl)) {}
    }
}
