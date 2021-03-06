package com.webdev.service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import com.webdev.dao.CustomerDao;
import com.webdev.model.Address;
import com.webdev.model.Customer;
import com.webdev.model.ShippingAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerSerivice {

    private final CustomerDao customerDao;

    @Autowired
    public CustomerSerivice(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Customer createCustomer(Customer customer) {
        return customerDao.add(customer);

    }

    public Customer getCustomerById(Integer id) throws EntityNotFoundException {
        return customerDao.get(id);
    }

    public Customer getCustomerByEmail(String email) throws EntityNotFoundException, NoResultException {
        return customerDao.getbyEmail(email);
    }

    public Customer updateCustomer(Integer id, Customer customer) {
        return customerDao.update(id, customer);
    }

    public Customer updateCustomer(Customer customer) {
        return customerDao.update(customer);
    }

    public Customer addAddressToCustomer(
            Customer customer,
            ShippingAddress shippingAddress) {

        Address address = new Address(
                shippingAddress.getFirstName(),
                shippingAddress.getLastName(),
                shippingAddress.getStreet(),
                shippingAddress.getCity(),
                shippingAddress.getState(),
                shippingAddress.getZip(),
                shippingAddress.getCountry());

        return customerDao.addAddress(customer, address);
    }
}
