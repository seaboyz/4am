package com.webdev.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.webdev.dao.CustomerDao;
import com.webdev.model.Address;
import com.webdev.model.Customer;
import com.webdev.model.ShippingAddress;
import com.webdev.utils.AddressConverter;

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

    public void addAddress(Integer customerId, ShippingAddress shippingAddress) {
        Customer customer = getCustomerById(customerId);

        Address address = AddressConverter.shippingAddressToAddress(shippingAddress);

        address.setCustomer(customer);
        customer.getAddresses().add(address);
        customerDao.update(customer);
    }
}
