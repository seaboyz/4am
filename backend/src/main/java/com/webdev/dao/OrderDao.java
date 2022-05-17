package com.webdev.dao;

import java.util.Optional;

import javax.persistence.EntityManager;

import com.webdev.model.Order;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao {
    @Autowired
    private EntityManager entityManager;

    // add a new order
    public Integer add(Order order) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        Integer id = (Integer) currentSession.save(order);
        currentSession.getTransaction().commit();
        currentSession.close();
        return id;
    }

    // get a order by id
    public Optional<Order> get(Integer id) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        Optional<Order> order = Optional.ofNullable(currentSession.get(Order.class, id));
        currentSession.getTransaction().commit();
        currentSession.close();
        return order;
    }

}
