package com.webdev.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.google.gson.Gson;
import com.webdev.model.Customer;
import com.webdev.service.AuthServiceWithJwt;
import com.webdev.service.CustomerSerivice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthController {

    private AuthServiceWithJwt authService;

    @Autowired
    public AuthController(CustomerSerivice customerService, AuthServiceWithJwt authService) {

        this.authService = authService;
    }

    @CrossOrigin()
    @GetMapping(value = "/auth/login")
    public ResponseEntity<String> login(@RequestHeader("Authorization") String auth) {

        try {
            String base64Credentials = auth.substring("Basic".length()).trim();
            byte[] credentialDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credentialDecoded, StandardCharsets.UTF_8);
            String[] credentialsArray = credentials.split(":");
            String email = credentialsArray[0];
            String password = credentialsArray[1];

            Customer customer = authService.login(email, password);

            customer.setPassword(null);
            customer.setOrders(null);
            customer.setAddresses(null);
            customer.setCartItems(null);
            Gson gson = new Gson();
            String json = gson.toJson(customer, Customer.class);

            System.out.println(json);

            return new ResponseEntity<String>(json, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin()
    @PostMapping(value = "/auth/register")
    public ResponseEntity<String> register(
            @RequestBody Customer customer) {

        try {
            Customer registeredCustomer = authService.register(customer);
            registeredCustomer.setPassword(null);
            registeredCustomer.setOrders(null);
            registeredCustomer.setAddresses(null);
            registeredCustomer.setCartItems(null);

            Gson gson = new Gson();
            String json = gson.toJson(registeredCustomer, Customer.class);

            return new ResponseEntity<String>(json, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
