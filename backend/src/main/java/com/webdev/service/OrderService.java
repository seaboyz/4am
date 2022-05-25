package com.webdev.service;

import com.webdev.dao.OrderDao;
import com.webdev.model.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 1. service can only access dao
// 2. service prefer to access its own dao
// 3. if there is a service for a the dao you can use the service instead of the dao directly
@Service
public class OrderService {

    private OrderDao orderDao;

    @Autowired
    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public Integer placeAOrder(
            Order order) {

        return orderDao.add(order);

    }

}
