package com.webdev.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.webdev.dao.CustomerDao;
import com.webdev.model.Customer;

public class CustomerService {

    private CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Customer getCustomerById(Integer id) {
        Optional<Customer> customer = customerDao.get(id);

        if (!customer.isPresent()) {
            throw new NoSuchElementException("Customer not found with id: " + id);
        }

        return customer.get();
    }
}
