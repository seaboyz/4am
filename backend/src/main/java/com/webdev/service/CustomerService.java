package com.webdev.service;

import java.util.NoSuchElementException;
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
    public Customer getCustomerById(Integer id) {
        Optional<Customer> customer = customerDao.get(id);

        if (!customer.isPresent()) {
            throw new NoSuchElementException("Customer not found with id: " + id);
        }

        return customer.get();
    }

    @Transactional
    public void addAddressToCustomer(Integer customerId, ShippingAddress shippingAddress) {
        Customer customer = getCustomerById(customerId);

        Address address = AddressConverter.shippingAddressToAddress(shippingAddress);

        address.setCustomer(customer);
        customer.getAddresses().add(address);
        customerDao.update(customer);
    }
}
