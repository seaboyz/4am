package com.webdev.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.webdev.model.OrderItem;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderItemDao {

    private final EntityManager entityManager;

    @Transactional
    public OrderItem add(OrderItem orderItem) {
        entityManager.unwrap(Session.class).save(orderItem);
        return orderItem;
    }

    public List<OrderItem> getOrderItemListByOrderId(Integer id) {
        return entityManager.unwrap(Session.class)
                .createQuery("from OrderItem where order_id = :id", OrderItem.class)
                .setParameter("id", id).list();

    }
}
