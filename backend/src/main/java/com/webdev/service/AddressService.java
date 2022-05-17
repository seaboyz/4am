package com.webdev.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.webdev.dao.AddressDao;
import com.webdev.model.Address;
import com.webdev.model.Customer;
import com.webdev.model.ShippingAddress;
import com.webdev.utils.AddressConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService {
    @Autowired
    private AddressDao addressDao;

    @Autowired
    private CustomerService customerService;

    @Transactional
    public void addAddressToCustomer(Integer customerId, ShippingAddress shippingAddress) {

        Optional<Customer> optionalCustomer = customerService.getCustomerById(customerId);

        if (!optionalCustomer.isPresent()) {
            throw new NoSuchElementException("Customer not found");
        }

        Customer customer = optionalCustomer.get();

        Address address = AddressConverter.shippingAddressToAddress(shippingAddress);

        address.setCustomer(customer);
        addressDao.add(address);

    }
}
