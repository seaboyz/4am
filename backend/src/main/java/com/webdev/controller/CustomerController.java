package com.webdev.controller;

import com.google.gson.Gson;
import com.webdev.model.Customer;
import com.webdev.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers/{id}")
    @ResponseBody
    public String getCustomersById(@PathVariable String id) {
        Integer customerId = Integer.parseInt(id);

        Customer customerFromDb = customerService.getCustomerById(customerId);

        Gson gson = new Gson();

        return gson.toJson(customerFromDb, Customer.class);

    }

}
