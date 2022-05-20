package com.webdev.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import com.webdev.model.Order;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderDao {

    private final EntityManager entityManager;

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
