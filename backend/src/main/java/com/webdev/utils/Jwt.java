package com.webdev.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.webdev.model.Customer;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Jwt {
    public static String getToken(Customer customer) throws JWTCreationException {
        // get secrect string from application.properties file
        Properties props = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream input = loader.getResourceAsStream("application.properties");
        try {
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("Fail loading props from application.properites file");
            return null;
        }

        // get
        String SECRECT = props.getProperty("ACCESS_TOKEN_SECRET");

        // get jwt token
        Algorithm algorithm = Algorithm.HMAC256(SECRECT);
        Builder builder = JWT.create().withIssuer("auth0").withClaim("email", customer.getEmail()).withClaim("id",
                customer.getId());

        return builder.sign(algorithm);
    }

    public static String getSecret() {
        // get secrect string from application.properties file
        Properties props = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream input = loader.getResourceAsStream("application.properties");
        try {
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("Fail loading props from application.properites file");
            return null;
        }

        // get
        String SECRECT = props.getProperty("ACCESS_TOKEN_SECRET");

        return SECRECT;
    }

    public static String encriptPassword(String password) {
        String salt = BCrypt.gensalt(10);
        String encryptedPassword = BCrypt.hashpw(password, salt);
        return encryptedPassword;
    }
}
