package com.example.handlers;

import com.example.service.admin.AdminService;
import com.example.utils.ColorPrint;
import com.example.utils.ScannerUtil;
import com.example.utils.UtilConstants;

public class AdminPanelHandler {
    private final AdminService adminService;

    public AdminPanelHandler(AdminService adminService) {
        this.adminService = adminService;
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
            case 5 -> handleBack();
            default -> ColorPrint.printlnRed("Неизвестное действие");
        }
    }

    private void handleUpdateUrlLimit() {
        String userLogin = ScannerUtil.readString("Введите логин: ");
        String shortUrl = ScannerUtil.readString("Введите короткую ссылку: ");
        Integer followLimit = ScannerUtil.readInt("Введите лимит переходов по ссылке: ");

    }

    private void handleUpdateUrlExpirationTime() {
        String userLogin = ScannerUtil.readString("Введите логин: ");
        String shortUrl = ScannerUtil.readString("Введите короткую ссылку: ");
        Integer expirationTime = ScannerUtil.readInt("Введите время жизни ссылки (в минутах): ");
    }

    private void handleDeleteUrl() {
        String userLogin = ScannerUtil.readString("Введите логин: ");
        String shortUrl = ScannerUtil.readString("Введите короткую ссылку: ");

    }

    private void handleDeleteAccount() {
        String userLogin = ScannerUtil.readString("Введите логин: ");
    }

    private void handleBack() {

    }

}
