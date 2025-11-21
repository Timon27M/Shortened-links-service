package com.example.UI;

import com.example.handlers.UrlCreationHandler;
import com.example.handlers.UrlFollowHandler;
import com.example.service.user.UserService;
import com.example.utils.ColorPrint;
import com.example.utils.ScannerUtil;
import com.example.utils.UtilConstants;
import java.io.IOException;
import java.net.URISyntaxException;

public class ConsoleUI {
	private final UserService userService;
	private boolean isStarted;

	public ConsoleUI(UserService userService) {
		this.userService = userService;
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
			System.out.println("Неверный ввод");
			return;
		}

		switch (actionNumber) {
			case 1 -> handleCreateUrl();
			case 2 -> handleFollowLink();
			case 4 -> stop();
			default -> System.out.println("Неизвестное действие");
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

	private void stop() {
		ColorPrint.printlnRed("Выход из программы");
		isStarted = false;
	}
}
