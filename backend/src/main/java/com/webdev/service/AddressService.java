package com.webdev.service;

import com.webdev.dao.AddressDao;
import com.webdev.model.Address;
import com.webdev.model.Customer;
import com.webdev.model.ShippingAddress;
import com.webdev.utils.AddressConverter;

public class AddressService {
    private AddressDao addressDao;
    private CustomerService customerService;

    public AddressService(AddressDao addressDao) {
        this.addressDao = addressDao;
    }
    
    public void addAddressToCustomer(Integer customerId, ShippingAddress shippingAddress) {

        Customer customer = customerService.getCustomerById(customerId);

        Address address = AddressConverter.shippingAddressToAddress(shippingAddress);

        address.setCustomer(customer);
        addressDao.add(address);
        

    
        
    }
}
