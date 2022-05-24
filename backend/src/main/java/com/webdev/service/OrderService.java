package com.webdev.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import com.webdev.dao.OrderDao;
import com.webdev.model.Customer;
import com.webdev.model.Order;
import com.webdev.model.OrderItem;
import com.webdev.model.Product;
import com.webdev.model.ShippingAddress;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

// 1. service can only access dao
// 2. service prefer to access its own dao
// 3. if there is a service for a the dao you can use the service instead of the dao directly
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDao orderDao;

    private final CustomerService customerService;

    private final ProductService productService;

    @Transactional
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

        customerService.addAddressToCustomer(customerId, shippingAddress);

        Integer orderId = orderDao.add(order);

        return orderId;
    }

}
