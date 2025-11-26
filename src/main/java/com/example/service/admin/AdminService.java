package com.example.service.admin;

import com.example.model.UrlInfo;
import com.example.model.UserData;
import com.example.repository.UserRepository;
import com.example.utils.ColorPrint;

public class AdminService {
    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateUrlLimit(String login, String shortUrl, Integer newLimit) {
        if (userRepository.checkUser(login)) {
            UserData userData = userRepository.findUser(login);

            if (userData.checkUserShortUrl(shortUrl)) {
                UrlInfo urlInfo = userData.getShortUrlData(shortUrl);
                urlInfo.setLimit(newLimit);
                userRepository.saveUser(login, userData);
                return;
            }
            ColorPrint.printlnRed("Ссылки не существует!");
        }

        ColorPrint.printlnRed("Пользователя не существует!");
    }

    public void updateUrlExpirationTime(String login, String shortUrl, Integer expirationTime) {
        if (userRepository.checkUser(login)) {
            UserData userData = userRepository.findUser(login);

            if (userData.checkUserShortUrl(shortUrl)) {
                UrlInfo urlInfo = userData.getShortUrlData(shortUrl);
                urlInfo.setExpirationTime(expirationTime);
                userRepository.saveUser(login, userData);
                return;
            }
            ColorPrint.printlnRed("Ссылки не существует!");
        }

        ColorPrint.printlnRed("Пользователя не существует!");
    }

    public void deleteUserUrl(String login, String shortUrl) {
        if (userRepository.checkUser(login)) {
            if (userRepository.findUser(login).checkUserShortUrl(shortUrl)) {
                userRepository.deleteUrlToUser(login, shortUrl);
                return;
            }
            ColorPrint.printlnRed("Ссылки не существует!");
        }

        ColorPrint.printlnRed("Пользователя не существует!");
    }

    public void deleteUser(String login) {
        if (userRepository.checkUser(login)) {
            userRepository.deleteUser(login);
            ColorPrint.printlnGreen("Пользователь успешно удален!");
        }
    }
}
