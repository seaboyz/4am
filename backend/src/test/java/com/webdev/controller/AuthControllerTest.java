package com.webdev.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Base64;

import com.google.gson.Gson;
import com.webdev.model.Customer;
import com.webdev.service.CustomerService;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {
        @MockBean
        private CustomerService customerService;
        @Autowired
        private MockMvc mockMvc;

        @Disabled
        @Test
        void testLogin() throws Exception {
                Customer customer = new Customer();
                customer.setId(1);
                customer.setEmail("test@test.com");
                customer.setUsername("test");
                customer.setPassword("123456");
                customer.setPhoneNumber("123456789");

                when(customerService.getCustomerByEmail("test@test.com")).thenReturn(customer);

                mockMvc.perform(get("/auth/login")
                                .header("Authorization",
                                                "Basic " + Base64.getEncoder()
                                                                .encodeToString("test:123456".getBytes())))
                                .andExpect(status().isOk());

        }

        @Test
        void testRegister() throws Exception {
                Customer newCustomer = new Customer(
                                "test",
                                "test@test.com",
                                "123456");

                Customer registeredCustomer = new Customer(
                                "test",
                                "test@test.com",
                                "123456");
                registeredCustomer.setId(1);

                when(customerService.createCustomer(newCustomer)).thenReturn(registeredCustomer);

                mockMvc.perform(post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new Gson().toJson(newCustomer)))
                                .andExpect(status().isCreated());

        }
}
