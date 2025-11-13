package com.example.handlers;

import com.example.service.UserService;
import com.example.utils.ColorPrint;
import com.example.utils.ScannerUtil;

public class UrlCreationHandler {
  private final UserService userService;

  public UrlCreationHandler(UserService userService) {
    this.userService = userService;
  }

  public void handle() {
    String userLogin = getValidLogin();
    if (userLogin == null) return;

    String originalUrl = getValidUrl();
    if (originalUrl == null) return;

    Integer followLimit = ScannerUtil.readInt("Введите лимит переходов по ссылке: ");
    Integer expirationTime = ScannerUtil.readInt("Введите время жизни ссылки (в минутах): ");

    createUrl(userLogin, originalUrl, followLimit, expirationTime);
  }

  private String getValidLogin() {
    String userLogin = ScannerUtil.readString("Введите логин: ");
    if (userLogin == null || userLogin.trim().isEmpty()) {
      ColorPrint.printlnRed("Логин не может быть пустым");
      return null;
    }

    return userLogin;
  }

  private String getValidUrl() {
    String originalUrl = ScannerUtil.readString("Введите url: ");
    if (originalUrl == null || originalUrl.trim().isEmpty()) {
      ColorPrint.printlnRed("URL не может быть пустым");
      return null;
    }
    return originalUrl.trim();
  }

  private void createUrl(
      String userLogin, String originalUrl, Integer followLimit, Integer expirationTime) {
    try {
      if (userService.checkUser(userLogin)) {
        // Пользователь существует - добавляем URL
        userService.addUrlToUser(userLogin, originalUrl, followLimit, expirationTime);
      } else {
        // Новый пользователь - создаем
        userService.createUser(userLogin, originalUrl, followLimit, expirationTime);
      }
    } catch (IllegalArgumentException e) {
      ColorPrint.printlnRed("Ошибка: " + e.getMessage());
    }
  }
}
