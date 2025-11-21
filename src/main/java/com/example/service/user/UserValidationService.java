package com.example.service.user;

import com.example.repository.UserRepository;
import com.example.utils.ColorPrint;

public class UserValidationService {
    private final UserRepository userRepository;

    public UserValidationService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateUserExists(String login) {
        if (!userRepository.checkUser(login)) {
            ColorPrint.printlnRed("Пользователь не найден");
        }
    }
}
