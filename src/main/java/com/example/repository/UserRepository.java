package com.example.repository;

import com.example.model.UserData;
import com.example.utils.HashMapJsonConverter;
import java.util.HashMap;

public class UserRepository {

	private final String filePath = "UsersDB.json";
	private HashMap<String, UserData> users;

	public UserRepository() {
		this.users = HashMapJsonConverter.loadHashMapFromJson(filePath, String.class, UserData.class);
	}

	public UserData findUser(String login) {
		return users.get(login);
	}

	public HashMap<String, UserData> getUsers() {
		return users;
	}

	public void saveUser(String login, UserData user) {
		users.put(login, user);
		saveUsers();
	}

	private void saveUsers() {
		HashMapJsonConverter.saveHashMapToJson(users, filePath);
	}

	public boolean checkUser(String login) {
		return users.containsKey(login);
	}

	public void deleteUrlToUser(String login, String shortUrl) {
		findUser(login).deleteUrl(shortUrl);
		saveUsers();
	}
}
