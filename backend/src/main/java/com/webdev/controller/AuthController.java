package com.webdev.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

import com.google.gson.Gson;
import com.webdev.model.Customer;
import com.webdev.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private CustomerService customerService;

    @Autowired
    public AuthController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/auth/login")
    public ResponseEntity<String> login(@RequestHeader("Authorization") String auth) {

        String base64Credentials = auth.substring("Basic".length()).trim();
        byte[] credentialDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credentialDecoded, StandardCharsets.UTF_8);
        String[] credentialsArray = credentials.split(":");
        String email = credentialsArray[0];
        String password = credentialsArray[1];

        Optional<Customer> customer = customerService.getCustomerByEmail(email);

        if (!customer.isPresent()) {
            return new ResponseEntity<String>(email + ": NOT FOUND", HttpStatus.NOT_FOUND);
        }

        if (!customer.get().getPassword().equals(password)) {
            return new ResponseEntity<String>("Email and password not match", HttpStatus.UNAUTHORIZED);
        }

        Customer customerObj = customer.get();
        customerObj.setPassword(null);
        customerObj.setOrders(null);
        customerObj.setAddresses(null);
        customerObj.setCartItems(null);
        Gson gson = new Gson();
        String json = gson.toJson(customerObj, Customer.class);

        return new ResponseEntity<String>(json, HttpStatus.OK);

    }

}
