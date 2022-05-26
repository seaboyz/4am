package com.webdev.controller;

import com.google.gson.Gson;
import com.webdev.model.Customer;
import com.webdev.service.CustomerSerivice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CustomerController {

    private CustomerSerivice customerService;

    @Autowired
    public CustomerController(CustomerSerivice customerService) {
        this.customerService = customerService;
    }

    @CrossOrigin()
    @PutMapping("/customers/{id}")
    public ResponseEntity<String> updateUserProile(
            @PathVariable("id") Integer id,
            @RequestBody Customer customer) {

        try {

            Customer updatedCustomer = customerService.updateCustomer(id, customer);

            updatedCustomer.setPassword(null);
            updatedCustomer.setOrders(null);
            updatedCustomer.setAddresses(null);
            // updatedCustomer.setCartItems(null);

            Gson gson = new Gson();

            return new ResponseEntity<String>(
                    gson.toJson(updatedCustomer, Customer.class), HttpStatus.OK);
        } catch (

        IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
