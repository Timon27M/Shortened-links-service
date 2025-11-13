package com.example.handlers;

import com.example.service.UserService;
import com.example.utils.ColorPrint;
import com.example.utils.ScannerUtil;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class UrlFollowHandler {
    private final UserService userService;

    public UrlFollowHandler(UserService userService) {
        this.userService = userService;
    }

    public void handle() throws URISyntaxException, IOException {
        String shortUrl = getValidShortUrl();
        if (shortUrl == null) return;

        String originalUrl = userService.getOriginalUrl(shortUrl);
        if (originalUrl == null) {
            ColorPrint.printlnRed("Ссылки не существует");
            return;
        }

        followUrl(originalUrl);
        userService.decrementLimit(shortUrl);
    }

    private String getValidShortUrl() {
        String shortUrl = ScannerUtil.readString("Введите короткую ссылку для перехода: ");

        if (shortUrl == null || shortUrl.isEmpty()) {
            ColorPrint.printlnRed("Ссылка не может быть пустой");
            return null;
        }
        return shortUrl;
    }

    private void followUrl(String originalUrl) throws URISyntaxException, IOException {
        ColorPrint.printlnGreen("Переход по ссылке: " + originalUrl);
        Desktop.getDesktop().browse(new URI(originalUrl));
    }
}
