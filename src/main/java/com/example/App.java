package com.example;

import com.example.model.UsersDB;
import com.example.utils.TextConstants;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

/** Hello world! */
public class App {
  //    public static void main(String[] args) throws URISyntaxException, IOException {
  //        Desktop.getDesktop().browse(new URI("https://ru.stackoverflow.com"));
  //    }

  static Scanner sc = new Scanner(System.in);

  public static void main(String[] args) throws URISyntaxException, IOException {
    System.out.println(TextConstants.INSTRUCTION_TEXT);
    System.out.print("Выберите действие: ");
    int actionNumber = sc.nextInt();
    sc.nextLine();

    if (actionNumber == 1) {
      String userLogin = scannerRun("Введите логин: ");
      String originalUrl = scannerRun("Введите url: ");
      if (UsersDB.checkUser(userLogin)) {
        UsersDB.addUserUrl(userLogin, originalUrl, 5);
        return;
      }
      UsersDB.addUser(userLogin, originalUrl, 5);
    }

    if (actionNumber == 2) {
        String shortUrl = scannerRun("Введите url для перехода: ");

        followLink(shortUrl);
    }
  }

  public static String scannerRun(String text) {
    System.out.print(text);
    return sc.nextLine();
  }

  public static void followLink(String url) throws URISyntaxException, IOException {
    String originalUrl = UsersDB.getUserUrl(url);
    if (originalUrl == null) {
        System.out.println("Ссылки не существует");
        return;
    }

    Desktop.getDesktop().browse(new URI(originalUrl));
  }
}
