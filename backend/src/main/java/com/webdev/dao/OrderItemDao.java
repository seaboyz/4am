package com.webdev.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.webdev.model.OrderItem;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemDao {
   

    @Autowired
    private EntityManager entityManager;

    public OrderItem add(OrderItem orderItem) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        currentSession.save(orderItem);
        currentSession.getTransaction().commit();
        currentSession.close();
        return orderItem;
    }

    public List<OrderItem> getOrderItemListByOrderId(Integer id) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        List<OrderItem> orderItemList = currentSession.createQuery("from OrderItem where order_id = :id", OrderItem.class)
                .setParameter("id", id).list();
        currentSession.getTransaction().commit();
        currentSession.close();
        return orderItemList;
    }
}
