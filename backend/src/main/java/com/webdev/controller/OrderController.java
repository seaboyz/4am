package com.webdev.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.webdev.model.Customer;
import com.webdev.model.Order;
import com.webdev.model.OrderItem;
import com.webdev.model.ShippingAddress;
import com.webdev.service.CustomerSerivice;
import com.webdev.service.OrderService;
import com.webdev.service.ProductService;
import com.webdev.utils.DoubleToIntDeserializer;

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

    private CustomerSerivice customerService;

    private ProductService productService;

    private OrderService orderService;

    @Autowired
    public OrderController(CustomerSerivice customerService, ProductService productService, OrderService orderService) {
        this.customerService = customerService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @CrossOrigin()
    @PostMapping(value = "/orders")
    public ResponseEntity<String> createOrder(@RequestBody String orderJson) {
        // System.out.println(orderJson);

        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(OrderItem.class, new DoubleToIntDeserializer());
        Gson gson = gsonBuilder.create();

        JsonObject orderJsonObject = gson.fromJson(orderJson, JsonObject.class);

        Integer customerId = orderJsonObject.get("customerId").getAsInt();

        Customer customer = customerService.getCustomerById(customerId);

        ShippingAddress shippingAddress = gson.fromJson(orderJsonObject.get("shippingAddress"), ShippingAddress.class);

        List<Map<String, Integer>> orderItemsList = gson
                .fromJson(orderJsonObject.get("orderItems"), new TypeToken<List<Map<String, Integer>>>() {
                }.getType());

        List<OrderItem> orderItems = new ArrayList<>();

        for (Map<String, Integer> orderItem : orderItemsList) {

            Integer productId = orderItem.get("productId");
            Integer quantity = orderItem.get("quantity");

            orderItems.add(new OrderItem(productService.getProductById(productId), quantity));
        }

        // System.out.println(customer);
        // System.out.println(shippingAddress);
        // System.out.println(orderItems);

        Order order = orderService.placeAOrder(new Order(customer, shippingAddress, orderItems));

        Customer updatedCustomer = customerService.addAddressToCustomer(customer, shippingAddress);

        System.out.println(updatedCustomer);

        gson = new Gson();

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("orderId", order.getId());

        String json = gson.toJson(jsonObject);

        System.out.println(json);

        return new ResponseEntity<String>(json, HttpStatus.OK);

    }

}
