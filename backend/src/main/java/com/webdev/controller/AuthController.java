package com.webdev.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import com.google.gson.Gson;
import com.webdev.model.Customer;
import com.webdev.service.CustomerService;

import org.hibernate.validator.constraints.CodePointLength;
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
    private CustomerService customerService;

    @Autowired
    public AuthController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @CrossOrigin()
    @GetMapping(value = "/auth/login")
    public ResponseEntity<String> login(@RequestHeader("Authorization") String auth) {

        String base64Credentials = auth.substring("Basic".length()).trim();
        byte[] credentialDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credentialDecoded, StandardCharsets.UTF_8);
        String[] credentialsArray = credentials.split(":");
        String email = credentialsArray[0];
        String password = credentialsArray[1];

        Customer customer;
        try {
            customer = customerService.getCustomerByEmail(email);
        } catch (EntityNotFoundException | NoResultException e) {
            return new ResponseEntity<String>(email + ": NOT FOUND", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (!customer.getPassword().equals(password)) {
            return new ResponseEntity<String>("Email and password not match", HttpStatus.UNAUTHORIZED);
        }

        customer.setPassword(null);
        customer.setOrders(null);
        customer.setAddresses(null);
        customer.setCartItems(null);
        Gson gson = new Gson();
        String json = gson.toJson(customer, Customer.class);

        return new ResponseEntity<String>(json, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/auth/register")
    public ResponseEntity<String> postMethodName(
            @RequestBody Customer customer) {
        System.out.println(customer);
        try {
            customerService.createCustomer(customer);
            return new ResponseEntity<String>("Customer created", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
