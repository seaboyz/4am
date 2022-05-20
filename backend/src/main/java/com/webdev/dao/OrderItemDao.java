package com.webdev.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.webdev.model.OrderItem;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderItemDao {

    private final EntityManager entityManager;

    public OrderItem add(OrderItem orderItem) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.save(orderItem);
        return orderItem;
    }

    public List<OrderItem> getOrderItemListByOrderId(Integer id) {
        Session currentSession = entityManager.unwrap(Session.class);
        List<OrderItem> orderItemList = currentSession
                .createQuery("from OrderItem where order_id = :id", OrderItem.class)
                .setParameter("id", id).list();
        return orderItemList;
    }
}
