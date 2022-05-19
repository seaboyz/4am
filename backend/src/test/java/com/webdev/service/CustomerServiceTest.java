package com.webdev.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.webdev.dao.CustomerDao;
import com.webdev.model.Address;
import com.webdev.model.Customer;
import com.webdev.model.ShippingAddress;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerDao customerDao;

    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        customerService = new CustomerService(customerDao);

    }

    @Test
    void testCreateCustomer() {

        Customer customer = new Customer();
        Customer createdCustomer = new Customer();
        createdCustomer.setId(1);

        when(customerDao.add(customer)).thenReturn(createdCustomer);
        assertEquals(createdCustomer, customerService.createCustomer(customer));

    }

    @Test
    void testGetCustomerByEmail() {

        Customer customer = new Customer();
        customer.setEmail("test@test.com");

        Customer customerFromDao = new Customer();
        customerFromDao.setEmail("test@test.com");

        when(customerDao.getbyEmail("test@test.com")).thenReturn(customerFromDao);

        assertEquals(customerFromDao, customerService.getCustomerByEmail("test@test.com"));

    }

    @Test
    void testGetCustomerById() {

        Customer customer = new Customer();
        customer.setId(1);

        Customer customerFromDao = new Customer();
        customerFromDao.setId(1);

        when(customerDao.get(1)).thenReturn(customerFromDao);

        assertEquals(customerFromDao, customerService.getCustomerById(1));

    }

    @Disabled
    @Test
    void testAddAddressToCustomer() {

        Address address = new Address();
        address.setStreet("123 Test Street");

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setStreet("123 Test Street");

        Customer customer = new Customer();
        customer.setId(1);
        customer.getAddresses().add(address);

        when(customerDao.get(1)).thenReturn(customer);

        when(customerDao.addAddress(customer, address)).thenReturn(customer);

        assertEquals(customer.getAddresses(), customerService.addAddressToCustomer(1, shippingAddress).getAddresses());

    }

}
