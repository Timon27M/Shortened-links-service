package com.example;

import com.example.repository.UserRepository;
import com.example.service.UserService;
import com.example.utils.ScannerUtil;
import com.example.utils.TextConstants;
import org.jetbrains.annotations.Nullable;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class App {
    private static UserRepository userRepository;
    private static UserService userService;

    public static void main(String[] args) throws URISyntaxException, IOException {
        userRepository = new UserRepository();
        userService = new UserService(userRepository);

        System.out.println(TextConstants.INSTRUCTION_TEXT);
        Integer actionNumber = ScannerUtil.readInt("Выберите действие: ");

        if (actionNumber == null) {
            System.out.println("Неверный ввод");
            return;
        }

        switch (actionNumber) {
            case 1 -> handleCreateUrl();
            case 2 -> handleFollowLink();
            default -> System.out.println("Неизвестное действие");
        }
    }

    private static void handleCreateUrl() {
        String userLogin = ScannerUtil.readString("Введите логин: ");
        if (userLogin == null || userLogin.trim().isEmpty()) {
            System.out.println("Логин не может быть пустым");
            return;
        }

        String originalUrl = ScannerUtil.readString("Введите url: ");
        if (originalUrl == null || originalUrl.trim().isEmpty()) {
            System.out.println("URL не может быть пустым");
            return;
        }

        @Nullable Integer followLimit = ScannerUtil.readInt("Введите лимит переходов по ссылке: ");

        try {
            if (userRepository.checkUser(userLogin)) {
                // Пользователь существует - добавляем URL
                userService.addUrlToUser(userLogin, originalUrl, followLimit);
            } else {
                // Новый пользователь - создаем
                userService.createUser(userLogin, originalUrl, followLimit);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

  public static void handleFollowLink() throws URISyntaxException, IOException {
      String shortUrl = ScannerUtil.readString("Введите короткую ссылку для перехода: ");
      if (shortUrl == null || shortUrl.trim().isEmpty()) {
          System.out.println("Ссылка не может быть пустой");
          return;
      }

      String originalUrl = userService.getOriginalUrl(shortUrl);
      if (originalUrl == null) {
          System.out.println("Ссылки не существует");
          return;
      }

      System.out.println("Переход по ссылке: " + originalUrl);
      Desktop.getDesktop().browse(new URI(originalUrl));
  }
}
