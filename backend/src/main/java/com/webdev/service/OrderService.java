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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 1. service can only access dao
// 2. service prefer to access its own dao
// 3. if there is a service for a the dao you can use the service instead of the dao directly
@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

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

        // addressService.addAddressToCustomer(customerId, shippingAddress);
        customerService.addAddressToCustomer(customerId, shippingAddress);

        Integer orderId = orderDao.add(order);

        return orderId;
    }

}
