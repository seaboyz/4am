package com.webdev.dao;

import javax.persistence.EntityNotFoundException;

import com.webdev.model.Order;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OrderDao {
    @Autowired
    private SessionFactory sessionFactory;

    // add a new order
    @Transactional
    public Integer add(Order order) {
        return (Integer) sessionFactory.getCurrentSession().save(order);

    }

    // get a order by id
    @Transactional(readOnly = true)
    public Order get(Integer id) throws EntityNotFoundException {
        return sessionFactory.getCurrentSession().get(Order.class, id);

    }

}
