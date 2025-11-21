package com.example;

import com.example.UI.ConsoleUI;
import com.example.repository.UserRepository;
import com.example.service.admin.AdminService;
import com.example.service.user.UserService;

public class App {

    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);

        AdminService adminService = new AdminService(userRepository);

        ConsoleUI ui = new ConsoleUI(userService, adminService);
        ui.start();
    }
}
