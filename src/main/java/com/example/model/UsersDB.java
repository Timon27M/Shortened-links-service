package com.example.model;

import com.example.utils.HashMapJsonConverter;

import java.util.HashMap;

public class UsersDB {

    private static final String filePath = "UsersDB.json";
    private static HashMap<String, UserData> usersDB = HashMapJsonConverter.loadHashMapFromJson(filePath);

    public static HashMap<String, UserData> getUsersDB() {
        return usersDB;
    }

    public static void addUser(String login, String originUrl, int limit) {
        UserData user = new UserData();
        user.addUserUrl(originUrl, limit);

        usersDB.put(login, user);
        saveUsers();
    }

    public static UserData getUser(String login) {
        return usersDB.get(login);
    }

    public static String getUserUrl(String login, String url) {
        UserData user = getUser(login);

        if (!user.checkUserShortUrl(url)) {
            System.out.println("URL не найден");
        }

        return user.getShortUrlData(url).toString();

    }

    public static void addUserUrl(String login, String originUrl) {
        UserData currentUser = getUser(login);

        if (currentUser == null) {
            System.out.println("Пользователь не найден");
            return;
        }

        currentUser.addUserUrl("urls", 5);

        usersDB.put(login, currentUser);
        saveUsers();
    }

    private static void saveUsers() {
        HashMapJsonConverter.saveHashMapToJson(usersDB, filePath);
    }

    public static boolean checkUser(String login) {
        return usersDB.containsKey(login);
    }
}
