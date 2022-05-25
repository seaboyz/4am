package com.webdev.controller;

import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.webdev.model.ShippingAddress;
import com.webdev.service.CustomerService;
import com.webdev.service.OrderService;
import com.webdev.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class OrderController {

    private CustomerService customerService;

    private ProductService productService;

    private OrderService orderService;

    @Autowired
    public OrderController(CustomerService customerService, ProductService productService, OrderService orderService) {
        this.customerService = customerService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @CrossOrigin()
    @PostMapping(value = "/orders")
    public ResponseEntity<String> createOrder(@RequestBody String orderJson) {

        Gson gson = new Gson();

        JsonObject orderJsonObject = gson.fromJson(orderJson, JsonObject.class);

        Integer customerId = orderJsonObject.get("customerId").getAsInt();

        ShippingAddress shippingAddress = gson.fromJson(orderJsonObject.get("shippingAddress"), ShippingAddress.class);

        @SuppressWarnings("unchecked")
        List<HashMap<String, Integer>> orderItems = gson
                .fromJson(orderJsonObject.get("orderItems"), List.class);

        System.out.println(customerId);
        System.out.println(shippingAddress);
        System.out.println(orderItems);

        return new ResponseEntity<String>("Order created", HttpStatus.OK);
    }

}
