package com.webdev.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import com.webdev.model.Order;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderDao {

    private final EntityManager entityManager;

    // add a new order
    @Transactional
    public Integer add(Order order) {
        return (Integer) entityManager.unwrap(Session.class).save(order);

    }

    // get a order by id
    @Transactional(readOnly = true)
    public Order get(Integer id) throws EntityNotFoundException {
        return entityManager.unwrap(Session.class).get(Order.class, id);

    }

}
