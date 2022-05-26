package com.webdev.service;

import javax.persistence.EntityNotFoundException;

import com.webdev.model.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthSerivice {
    private CustomerSerivice customerService;

    @Autowired
    public AuthSerivice(CustomerSerivice customerService) {
        this.customerService = customerService;
    }

    public Customer login(String email, String password) throws EntityNotFoundException {
        if (email == null || password == null) {
            throw new IllegalArgumentException("Email or password cannot be null");
        }

        Customer customer = customerService.getCustomerByEmail(email);

        if (!customer.getPassword().equals(password)) {
            throw new IllegalArgumentException("Wrong password");
        }

        return customer;

    }

    public Customer register(Customer customer) {
        return customerService.createCustomer(customer);
    }

}
