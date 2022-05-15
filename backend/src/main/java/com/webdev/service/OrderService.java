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
    }

    // what is the minimum information needed to create an order?
    // 1. Customer(id)
    // 2. Address(address)
    // 3. Product(id)
    // 4. Quantity(int)

    public Integer placeAOrder(Integer customerId, ShippingAddress shippingAddress,
            HashMap<Integer, Integer> products) {

        Customer customer = customerService.getCustomerById(customerId);

        Set<OrderItem> orderItemList = new HashSet<>();

        products.forEach((productId, quantity) -> {
            Product product = productService.getProductById(productId);
            orderItemList.add(new OrderItem(product, quantity));
        });

        Order order = new Order(customer, shippingAddress, orderItemList);

        addressService.addAddressToCustomer(customerId, shippingAddress);

        Integer orderId = orderDao.add(order);

        return orderId;
    }

}
