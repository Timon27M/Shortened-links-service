package com.example.service;

import com.example.model.UrlInfo;
import com.example.model.UserData;
import com.example.repository.UserRepository;
import com.example.utils.JavaUrlValidator;
import java.util.HashMap;

public class UserService {
    private final UserRepository userRepository;
    private final UrlService urlService;

    public UserService(UserRepository userRepository, UrlService urlService) {
        this.userRepository = userRepository;
        this.urlService = urlService;
    }

    public void createUser(String login, String originalUrl, Integer limit) {
        if (!JavaUrlValidator.isValidUrl(originalUrl)) {
            throw new IllegalArgumentException("Невалидная ссылка");
        }

        UserData user = new UserData();
        String shortUrl = user.addUrl(originalUrl, limit);
        UrlInfo userData = user.getShortUrlData(shortUrl);

        userRepository.saveUser(login, user);
        urlService.saveUrl(shortUrl, userData);
        System.out.println("Создана короткая ссылка: " + shortUrl);
    }

    public void addUrlToUser(String login, String originalUrl, Integer limit) {
        if (!JavaUrlValidator.isValidUrl(originalUrl)) {
            throw new IllegalArgumentException("Невалидная ссылка");
        }

        if (!userRepository.checkUser(login)) {
            throw new IllegalArgumentException("Пользователь не найден");
        }

        UserData user = userRepository.findUser(login);

        String shortUrl = user.addUrl(originalUrl, limit);
        userRepository.saveUser(login, user);
        System.out.println("Создана короткая ссылка: " + shortUrl);
    }

    public HashMap<String, UrlInfo> getUrlInfo(String login, String shortUrl) {
        if (!userRepository.checkUser(login)) {
            throw new IllegalArgumentException("Пользователь не найден");
        }

        return userRepository.findUser(login).getUrls();
    }

    public String getOriginalUrl(String shortUrl) {
        String originalUrl = urlService.getOriginalUrl(shortUrl);;

        // Внутренний поиск если в UrlsDB не найдено
        if (originalUrl == null) {
            for (UserData user : userRepository.getUsers().values()) {
            HashMap<String, UrlInfo> urls = user.getUrls();

            if (urls.containsKey(shortUrl)) {
                UrlInfo urlInfo = urls.get(shortUrl);
                return urlInfo.getOriginalUrl();
            }
        }
        }

        return originalUrl;
    }
}
