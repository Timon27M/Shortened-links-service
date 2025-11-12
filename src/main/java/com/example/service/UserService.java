package com.example.service;

import com.example.model.UrlInfo;
import com.example.model.UserData;
import com.example.repository.UserRepository;
import com.example.utils.JavaUrlValidator;
import java.util.HashMap;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(String login, String originalUrl, Integer limit) {
        if (!JavaUrlValidator.isValidUrl(originalUrl)) {
            throw new IllegalArgumentException("Невалидная ссылка");
        }

        UserData user = new UserData();
        String shortUrl = user.addUrl(originalUrl, limit);

        userRepository.saveUser(login, user);
        System.out.println("Создана короткая ссылка: " + shortUrl);
    }

    public void addUrlToUser(String login, String originalUrl, Integer limit) {
        if (!JavaUrlValidator.isValidUrl(originalUrl)) {
            throw new IllegalArgumentException("Невалидная ссылка");
        }

        UserData user = userRepository.findUser(login);
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не найден");
        }

        String shortUrl = user.addUrl(originalUrl, limit);
        userRepository.saveUser(login, user);
        System.out.println("Создана короткая ссылка: " + shortUrl);
    }

    public String getOriginalUrl(String shortUrl) {
        for (UserData user : userRepository.getUsers().values()) {
            HashMap<String, UrlInfo> urls = user.getUrls();

            if (urls.containsKey(shortUrl)) {
                UrlInfo urlInfo = urls.get(shortUrl);
                return urlInfo.getOriginalUrl();
            }
        }

        return null;
    }
}
