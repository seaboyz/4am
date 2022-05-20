package com.webdev.service;

import javax.persistence.EntityNotFoundException;

import com.webdev.model.Customer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthSerivice {
    private CustomerService customerService;

    @Transactional(readOnly = true)
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

    @Transactional
    public Customer register(Customer customer) {
        return customerService.createCustomer(customer);
    }

}
