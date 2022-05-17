package com.webdev.service;

import java.util.Optional;

import com.webdev.dao.CustomerDao;
import com.webdev.model.Address;
import com.webdev.model.Customer;
import com.webdev.model.ShippingAddress;
import com.webdev.utils.AddressConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private CustomerDao customerDao;

    @Autowired
    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional
    public Customer createCustomer(Customer customer) {
        return customerDao.add(customer);
    }

    @Transactional
    public Optional<Customer> getCustomerById(Integer id) {
        return customerDao.get(id);

    }

    @Transactional
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerDao.getbyEmail(email);
    }

    @Transactional
    public void addAddressToCustomer(Integer customerId, ShippingAddress shippingAddress) {
        Optional<Customer> optionalCustomer = getCustomerById(customerId);
        if (!optionalCustomer.isPresent()) {
            return;
        }

        Customer customer = optionalCustomer.get();

        Address address = AddressConverter.shippingAddressToAddress(shippingAddress);

        customerDao.addAddress(customer, address);
    }
}
