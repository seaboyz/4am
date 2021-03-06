package com.webdev.dao;

import javax.persistence.EntityNotFoundException;

import com.webdev.model.Order;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao {
    @Autowired
    private SessionFactory sessionFactory;

    // add a new order

    public Order add(Order order) {
        sessionFactory.getCurrentSession().save(order);
        return order;

    }

    // get a order by id

    public Order get(Integer id) throws EntityNotFoundException {
        return sessionFactory.getCurrentSession().get(Order.class, id);

    }

}
