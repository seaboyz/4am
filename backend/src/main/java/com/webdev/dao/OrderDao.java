package com.webdev.dao;

import java.util.Optional;

import com.webdev.model.Order;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class OrderDao {
    private SessionFactory sessionFactory;

    public OrderDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // add a new order
    public Integer add(Order order) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Integer id = (Integer) session.save(order);
        session.getTransaction().commit();
        session.close();
        return id;
    }

    // get a order by id
    public Optional<Order> get(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Optional<Order> order = Optional.ofNullable(session.get(Order.class, id));
        session.getTransaction().commit();
        session.close();
        return order;
    }

}
