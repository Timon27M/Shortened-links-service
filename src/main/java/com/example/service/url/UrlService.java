package com.example.service.url;

import com.example.model.UrlInfo;
import com.example.model.UserData;
import com.example.repository.UserRepository;

public class UrlService {
    private final UserRepository userRepository;
    private final UrlLimitService urlLimitService;
    private final UrlExpirationService urlExpirationService;

    public UrlService(UserRepository userRepository, UrlLimitService urlLimitService, UrlExpirationService urlExpirationService) {
        this.userRepository = userRepository;
        this.urlLimitService = urlLimitService;
        this.urlExpirationService = urlExpirationService;
    }

    public String addUrl(String login, String originalUrl, Integer limit, Integer expiration) {
        UserData user = userRepository.findUser(login);
        return user.addUrl(originalUrl, limit, expiration);
    }

//    public String resolveShortUrl(String shortUrl) {
//        UserData user = findUserByShortUrl(shortUrl);
//
//        UrlInfo info = user.getUrls().get(shortUrl);
//
//        if (!urlExpirationService.isUrlActive(info)) {
//            deleteShortUrl(shortUrl);
//            return null;
//        }
//
//        if (!urlLimitService.checkLimit(info)) {
//            deleteShortUrl(shortUrl);
//            return null;
//        }
//
//        urlLimitService.decrement(info);
//        userRepository.saveUser(user.getLogin(), user);
//
//        return info.getOriginalUrl();
//    }
}
