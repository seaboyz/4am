package com.webdev.dao;

import java.util.List;

import com.webdev.model.OrderItem;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class OrderItemDao {
    private SessionFactory sessionFactory;

    public OrderItemDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public OrderItem add(OrderItem orderItem) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(orderItem);
        session.getTransaction().commit();
        session.close();
        return orderItem;
    }

    public List<OrderItem> getOrderItemListByOrderId(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<OrderItem> orderItemList = session.createQuery("from OrderItem where order_id = :id", OrderItem.class)
                .setParameter("id", id).list();
        session.getTransaction().commit();
        session.close();
        return orderItemList;
    }
}
