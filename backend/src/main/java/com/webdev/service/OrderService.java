package com.webdev.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.webdev.dao.OrderDao;
import com.webdev.model.Customer;
import com.webdev.model.Order;
import com.webdev.model.OrderItem;
import com.webdev.model.Product;
import com.webdev.model.ShippingAddress;

// 1. service can only access dao
// 2. service prefer to access its own dao
// 3. if there is a service for a the dao you can use the service instead of the dao directly

public class OrderService {
    private OrderDao orderDao;
    private CustomerService customerService;
    private ProductService productService;
    private AddressService addressService;

    public OrderService(OrderDao orderDao, CustomerService customerService, ProductService productService,
            AddressService addressService) {
        this.orderDao = orderDao;
        this.customerService = customerService;
        this.productService = productService;
        this.addressService = addressService;
    }

    // what is the minimum information needed to create an order?
    // 1. Customer(id)
    // 2. Address(address)
    // 3. Product(id)
    // 4. Quantity(int)

    public Integer placeAOrder(
            Integer customerId,
            ShippingAddress shippingAddress,
            HashMap<Integer, Integer> itemList) {

        Customer customer = customerService.getCustomerById(customerId);

        Set<OrderItem> orderItemList = new HashSet<>();

        itemList.forEach((productId, quantity) -> {
            Product product = productService.getProductById(productId);
            orderItemList.add(new OrderItem(product, quantity));
        });

        Order order = new Order(customer, shippingAddress, orderItemList);

        // note: instead of adding customer to the address, we can add the address the
        // customer;
        // because the customer has a one-to-many relationship with the address, and has
        // cascade.all
        // so the address will be added to the customer automatically

        //// addressService.addAddressToCustomer(customerId, shippingAddress);
        customerService.addAddress(customerId, shippingAddress);

        Integer orderId = orderDao.add(order);

        return orderId;
    }

}
