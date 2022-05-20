package com.webdev.dao;

import java.util.List;

import com.webdev.model.OrderItem;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OrderItemDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public OrderItem add(OrderItem orderItem) {
        sessionFactory.getCurrentSession().save(orderItem);
        return orderItem;
    }

    public List<OrderItem> getOrderItemListByOrderId(Integer id) {
        return sessionFactory.getCurrentSession()
                .createQuery("from OrderItem where order_id = :id", OrderItem.class)
                .setParameter("id", id).list();

    }
}
