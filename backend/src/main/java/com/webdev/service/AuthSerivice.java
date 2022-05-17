package com.webdev.service;

import java.util.Optional;

import com.webdev.model.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthSerivice {
    private CustomerService customerService;

    @Autowired
    public AuthSerivice(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Transactional
    public Customer login(String email, String password) {
        Optional<Customer> optionalCustomer = customerService.getCustomerByEmail(email);
        if (!optionalCustomer.isPresent()) {
            throw new IllegalArgumentException("No customer with email " + email);
        }

        Customer customer = optionalCustomer.get();
        if (!customer.getPassword().equals(password)) {
            throw new IllegalArgumentException("Wrong password");
        }

        return customer;
    }

    @Transactional
    public Optional<Customer> register(Customer customer) {
        return customerService.createCustomer(customer);
    }

}
