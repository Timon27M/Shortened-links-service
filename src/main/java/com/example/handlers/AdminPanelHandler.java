package com.example.handlers;

import com.example.model.UrlInfo;
import com.example.service.admin.AdminService;
import com.example.utils.ColorPrint;
import com.example.utils.ScannerUtil;
import com.example.utils.UtilConstants;

public class AdminPanelHandler {
  private final AdminService adminService;
  private final String userLogin;

  public AdminPanelHandler(AdminService adminService, String userLogin) {
    this.adminService = adminService;
    this.userLogin = userLogin;
  }

  public void handle() {
    ColorPrint.printlnBlue(UtilConstants.ADMIN_INSTRUCTION_TEXT);
    Integer actionNumber = ScannerUtil.readInt("Выберите действие: ");

    if (actionNumber == null) {
      ColorPrint.printlnRed("Неверный ввод");
      return;
    }

    switch (actionNumber) {
      case 1 -> handleUpdateUrlLimit();
      case 2 -> handleUpdateUrlExpirationTime();
      case 3 -> handleDeleteUrl();
      case 4 -> handleDeleteAccount();
      case 5 -> handleUrlParameters();
      case 6 -> handleBack();
      default -> ColorPrint.printlnRed("Неизвестное действие");
    }
  }

  private void handleUpdateUrlLimit() {
    String shortUrl = ScannerUtil.readString("Введите короткую ссылку: ");
    Integer followLimit = ScannerUtil.readInt("Введите лимит переходов по ссылке: ");

    adminService.updateUrlLimit(userLogin, shortUrl, followLimit);
  }

  private void handleUpdateUrlExpirationTime() {
    String shortUrl = ScannerUtil.readString("Введите короткую ссылку: ");
    Integer expirationTime = ScannerUtil.readInt("Введите время жизни ссылки (в минутах): ");

    adminService.updateUrlExpirationTime(userLogin, shortUrl, expirationTime);
  }

  private void handleDeleteUrl() {
    String shortUrl = ScannerUtil.readString("Введите короткую ссылку: ");

    adminService.deleteUserUrl(userLogin, shortUrl);
  }

  private void handleDeleteAccount() {
    adminService.deleteUser(userLogin);
  }

  private void handleUrlParameters() {
    String shortUrl = ScannerUtil.readString("Введите короткую ссылку: ");

    UrlInfo urlInfo = adminService.getUserUrlInfo(userLogin, shortUrl);

    if (urlInfo != null) {
      ColorPrint.printlnWhite("");
      ColorPrint.printlnGreen("Параметры ссылки " + shortUrl + ": ");
      ColorPrint.printlnWhite("   Лимит: " + urlInfo.getLimit());
      ColorPrint.printlnWhite("   Оригинальная ссылка: " + urlInfo.getOriginalUrl());
      ColorPrint.printlnWhite("   Ссылка создана: " + urlInfo.getDate());
      ColorPrint.printlnWhite("   Время жизни ссылки (в минутах): " + urlInfo.getExpirationTime());
      ColorPrint.printlnWhite("");
    }
  }

  private void handleBack() {}
}
