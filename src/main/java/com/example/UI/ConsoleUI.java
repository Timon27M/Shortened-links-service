package com.example.UI;

import com.example.handlers.AdminPanelHandler;
import com.example.handlers.UrlCreationHandler;
import com.example.handlers.UrlFollowHandler;
import com.example.service.admin.AdminService;
import com.example.service.user.UserService;
import com.example.utils.ColorPrint;
import com.example.utils.ScannerUtil;
import com.example.utils.UtilConstants;
import java.io.IOException;
import java.net.URISyntaxException;

public class ConsoleUI {
	private final UserService userService;
    private final AdminService adminService;
	private boolean isStarted;

	public ConsoleUI(UserService userService, AdminService adminService) {
		this.userService = userService;
        this.adminService = adminService;
		this.isStarted = true;
	}

	public void start() {
		while (isStarted) {
			showMenu();
			handleUserChoice();
		}
	}

	private void showMenu() {
		ColorPrint.printlnBlue(UtilConstants.INSTRUCTION_TEXT);
	}

	private void handleUserChoice() {
		Integer actionNumber = ScannerUtil.readInt("Выберите действие: ");

		if (actionNumber == null) {
			ColorPrint.printlnRed("Неверный ввод");
			return;
		}

		switch (actionNumber) {
			case 1 -> handleCreateUrl();
			case 2 -> handleFollowLink();
            case 3 -> handleAdminActions();
			case 4 -> stop();
			default -> ColorPrint.printlnRed("Неизвестное действие");
		}
	}

	private void handleCreateUrl() {
		UrlCreationHandler handler = new UrlCreationHandler(userService);
		handler.handle();
	}

	private void handleFollowLink() {
		try {
			UrlFollowHandler handler = new UrlFollowHandler(userService);
			handler.handle();
		} catch (URISyntaxException | IOException e) {
			ColorPrint.printlnRed("Ошибка при переходе по ссылке: " + e.getMessage());
		}
	}

    private void handleAdminActions() {
        String userLogin = ScannerUtil.readString("Введите логин: ");
        if (userService.checkUser(userLogin)) {
            String userId = ScannerUtil.readString("Введите UUID: ");
            if (userService.checkUserUUID(userLogin, userId)) {
                AdminPanelHandler handler = new AdminPanelHandler(adminService, userLogin);
                handler.handle();
            } else {
                ColorPrint.printlnRed("Неверный UUID");
            }
        } else {
            ColorPrint.printlnRed("Пользователя не сущесствует");
        }
    }

	private void stop() {
		ColorPrint.printlnRed("Выход из программы");

		isStarted = false;
	}
}
