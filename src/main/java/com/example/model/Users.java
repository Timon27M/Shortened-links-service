//package com.example.model;
//
//import com.example.utils.CustomUrlShortener;
//import com.example.utils.HashMapJsonConverter;
//
//import java.util.HashMap;
//import java.util.UUID;
//
//public class Users {
//
//    private static final String filePath = "UsersDB.json";
//    private static HashMap<String, HashMap<String, Object>> usersDB = HashMapJsonConverter.loadHashMapFromJson(filePath);
//
//    public static HashMap<String, HashMap<String, Object>> getUsersDB() {
//        return usersDB;
//    }
//
//    public static void addUser(String login, String url) {
//        UUID userId = UUID.randomUUID();
//        HashMap<String, Object> userData = new HashMap<>();
//
//        userData.put("id", userId.toString());
//        usersDB.put(login, userData);
//        addUserUrl(login, url);
//        saveUsers();
//    }
//
//    public static HashMap<String, Object> getUser(String login) {
//        return usersDB.get(login);
//    }
//
//    public static String getUserUrl(String login, String url) {
//        HashMap<String, Object> urls = getUserUrls(login);
//
//        if (!urls.containsKey(url)) {
//            System.out.println("URL не найден");
//        }
//
//        return urls.get(url).toString();
//
//    }
//
//    private static HashMap<String, Object> getUserUrls(String login) {
//        HashMap<String, Object> user = UsersDB.getUser(login);
//        HashMap<String, Object> urls = (HashMap<String, Object>) user.get("urls");
//
//        return urls;
//    }
//
//    public static void addUserUrl(String login, String url) {
//        HashMap<String, Object> currentUser = getUser(login);
//
//        if (!currentUser.containsKey("urls")) {
//            currentUser.put("urls", new HashMap<>());
//        }
//
//        if (checkUserOriginUrl(currentUser, url)) {
//            System.out.println("Данный url уже зарегистрирован");
//            return;
//        }
//
//        HashMap<String, String> urls = (HashMap<String, String>) currentUser.get("urls");
//        String shortUrl = CustomUrlShortener.shortenUrl(url);
//
//
//        urls.put(shortUrl, url);
//        usersDB.put(login, currentUser);
//        saveUsers();
//    }
//
//    private static void saveUsers() {
//        HashMapJsonConverter.saveHashMapToJson(usersDB, filePath);
//    }
//
//    public static boolean checkUser(String login) {
//        return usersDB.containsKey(login);
//    }
//
//    private static boolean checkUserOriginUrl(HashMap<String, Object> user, String url) {
//        HashMap<String, String> urls = (HashMap<String, String>) user.get("urls");
//        return urls.containsValue(url);
//    }

//}