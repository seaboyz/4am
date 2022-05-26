package com.webdev.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Encript {
    public static String encriptPassword(String password) {
        String salt = BCrypt.gensalt(10);
        String encryptedPassword = BCrypt.hashpw(password, salt);
        return encryptedPassword;
    }

    public static boolean checkPassword(String password, String encryptedPassword) {
        return BCrypt.checkpw(password, encryptedPassword);
    }
}
