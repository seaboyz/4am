package com.webdev.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

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
        return (Integer) currentSession.save(order);

    }

    // get a order by id
    public Order get(Integer id) throws EntityNotFoundException {
        Session currentSession = entityManager.unwrap(Session.class);
        return currentSession.get(Order.class, id);

    }

}
