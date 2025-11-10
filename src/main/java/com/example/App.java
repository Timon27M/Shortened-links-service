package com.example;

import com.example.model.UserData;
import com.example.model.UsersDB;
import com.example.utils.TextConstants;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Scanner;
/**
 * Hello world!
 */
public class App {
//    public static void main(String[] args) throws URISyntaxException, IOException {
//        Desktop.getDesktop().browse(new URI("https://ru.stackoverflow.com"));
//    }

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println(TextConstants.INSTRUCTION_TEXT);
        System.out.print("Выберите действие: ");
        int actionNumber = sc.nextInt();

        if (actionNumber == 1) {
            String userLogin = scannerRun("Введите логин: ");
            String originalUrl = scannerRun("Введите url: ");
            if (UsersDB.checkUser(userLogin)) {
                UsersDB.addUserUrl(userLogin, originalUrl);
                return;
            }
            UsersDB.addUser(userLogin, originalUrl, 5);
        }
    }

    public static String scannerRun(String text) {
        System.out.print(text);
        return sc.nextLine();
    }

    public static void handleClick(String login, String url) throws URISyntaxException, IOException {
        String originUrl = UsersDB.getUserUrl(login, url);

        Desktop.getDesktop().browse(new URI(originUrl));
    }
}
