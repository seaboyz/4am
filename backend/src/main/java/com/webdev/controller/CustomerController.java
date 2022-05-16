package com.webdev.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    @GetMapping("/customers")
    public String getCustomers() {
        return "customers";
    }
}
