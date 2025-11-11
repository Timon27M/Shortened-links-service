package com.example.model;

import com.example.utils.HashMapJsonConverter;
import com.example.utils.JavaUrlValidator;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;

public class UsersDB {

  private static final String filePath = "UsersDB.json";
  private static HashMap<String, UserData> usersDB =
      HashMapJsonConverter.loadHashMapFromJson(filePath);

  public static void addUser(String login, String originalUrl, int limit) {
    if (!JavaUrlValidator.isValidUrl(originalUrl)) {
      System.out.println("Невалидная ссылка");
      return;
    }

    UserData user = new UserData();
    String shortUrl = user.addUrl(originalUrl, limit);

    usersDB.put(login, user);
    saveUsers();

      System.out.println("Создана короткая ссылка: " + shortUrl);
  }

  public static UserData getUser(String login) {
    return usersDB.get(login);
  }

  public static String getUserUrl(String shortUrl) {
//    UserData user = getUser(login);
//
//    if (!user.checkUserShortUrl(url)) {
//      System.out.println("URL не найден");
//    }
//
//    return user.getShortUrlData(url).toString();

      for (UserData user : usersDB.values()) {
          HashMap<String, UrlInfo> urls = user.getUrls();

          if (urls.containsKey(shortUrl)) {
              UrlInfo urlInfo = urls.get(shortUrl);
              return urlInfo.getOriginalUrl();
          }
      }

      return null;
  }

  public static void addUserUrl(String login, String originalUrl, @Nullable Integer limit) {
    if (!JavaUrlValidator.isValidUrl(originalUrl)) {
      System.out.println("Невалидная ссылка");
      return;
    }
    UserData currentUser = getUser(login);

    if (currentUser == null) {
      System.out.println("Пользователь не найден");
      return;
    }

    String shortUrl = currentUser.addUrl(originalUrl, limit);

    usersDB.put(login, currentUser);
    saveUsers();

    System.out.println("Создана короткая ссылка: " + shortUrl);
  }

  private static void saveUsers() {
    HashMapJsonConverter.saveHashMapToJson(usersDB, filePath);
  }

  public static boolean checkUser(String login) {
    return usersDB.containsKey(login);
  }
}
