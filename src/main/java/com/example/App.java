package com.example;

import com.example.UI.ConsoleUI;
import com.example.repository.UserRepository;
import com.example.service.UserService;

public class App {
  //  private static UserRepository userRepository;
  //  private static UserService userService;
  //
  //  private static UrlRepository urlRepository;
  //  private static UrlService urlService;

  public static void main(String[] args) {
//    UrlRepository urlRepository = new UrlRepository();
//    UrlService urlService = new UrlService(urlRepository);

    UserRepository userRepository = new UserRepository();
    UserService userService = new UserService(userRepository);

    ConsoleUI ui = new ConsoleUI(userService);
    ui.start();

    //    urlRepository = new UrlRepository();
    //    urlService = new UrlService(urlRepository);
    //
    //    userRepository = new UserRepository();
    //    userService = new UserService(userRepository, urlService);
    //
    //    boolean isRunProgram = true;
    //
    //    while (isRunProgram) {
    //      System.out.println(UtilConstants.INSTRUCTION_TEXT);
    //      Integer actionNumber = ScannerUtil.readInt("Выберите действие: ");
    //
    //      if (actionNumber == null) {
    //        System.out.println("Неверный ввод");
    //        return;
    //      }
    //
    //      switch (actionNumber) {
    //        case 1 -> handleCreateUrl();
    //        case 2 -> handleFollowLink();
    //        case 4 -> {
    //          System.out.println("Выход из программы");
    //          isRunProgram = false;
    //        }
    //        default -> System.out.println("Неизвестное действие");
    //      }
    //    }
  }

  //  private static void handleCreateUrl() {
  //    String userLogin = ScannerUtil.readString("Введите логин: ");
  //    if (userLogin == null || userLogin.trim().isEmpty()) {
  //      System.out.println("Логин не может быть пустым");
  //      return;
  //    }
  //
  //    String originalUrl = ScannerUtil.readString("Введите url: ");
  //    if (originalUrl == null || originalUrl.trim().isEmpty()) {
  //      System.out.println("URL не может быть пустым");
  //      return;
  //    }
  //
  //    Integer followLimit = ScannerUtil.readInt("Введите лимит переходов по ссылке: ");
  //
  //    Integer expirationTime = ScannerUtil.readInt("Введите время жизни ссылки (в минутах): ");
  //
  //    try {
  //      if (userRepository.checkUser(userLogin)) {
  //        // Пользователь существует - добавляем URL
  //        userService.addUrlToUser(userLogin, originalUrl, followLimit, expirationTime);
  //      } else {
  //        // Новый пользователь - создаем
  //        userService.createUser(userLogin, originalUrl, followLimit, expirationTime);
  //      }
  //    } catch (IllegalArgumentException e) {
  //      System.out.println("Ошибка: " + e.getMessage());
  //    }
  //  }
  //
  //  public static void handleFollowLink() throws URISyntaxException, IOException {
  //    String shortUrl = ScannerUtil.readString("Введите короткую ссылку для перехода: ");
  //    if (shortUrl == null || shortUrl.trim().isEmpty()) {
  //      System.out.println("Ссылка не может быть пустой");
  //      return;
  //    }
  //
  //    String originalUrl = userService.getOriginalUrl(shortUrl);
  //    if (originalUrl == null) {
  //      System.out.println("Ссылки не существует");
  //      return;
  //    }
  //
  //    System.out.println("Переход по ссылке: " + originalUrl);
  //    Desktop.getDesktop().browse(new URI(originalUrl));
  //    userService.decrimentLimit(shortUrl);
  //  }
}
