package com.webdev.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.webdev.model.Customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthSeriviceTest {

    @Mock
    CustomerService customerService;

    private AuthSerivice authSerivice;

    @BeforeEach
    public void setUp() {
        authSerivice = new AuthSerivice(customerService);
    }

    @Test
    void testLogin() {
        Customer customer = new Customer(
                "test", "test@test.com", "123456",
                "555-555-5555");

        when(customerService.getCustomerByEmail(anyString())).thenReturn(customer);

        assertThrows(IllegalArgumentException.class, () -> {
            authSerivice.login("", "");
        });

        assertEquals(customer, authSerivice.login("test@test.com", "123456"));
    }

    @Test
    void testRegister() {
        Customer customer = new Customer(
                "test", "test@test.com", "123456",
                "555-555-5555");

        when(customerService.createCustomer(customer)).thenReturn(customer);

        assertEquals(customer, authSerivice.register(customer));
    }
}
