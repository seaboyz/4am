package com.webdev.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private Customer customer;

    // shipping address
    @NonNull
    @Embedded()
    private ShippingAddress shippingAddress;

    // once the order is saved, the order items are saved in the order_items table
    @NonNull
    @OneToMany(mappedBy = "order")
    private Set<OrderItem> orderItemList = new HashSet<OrderItem>();

    @Column(name = "total", nullable = false)
    private Double total;

    // there is no meaning to have an order without a customer
    // there is no meaning to have an order without an order item
    // there is no meaning to have an order without a shipping address
    // total should be calculated from the order items
    public Order(Customer customer, ShippingAddress shippingAddress, Set<OrderItem> orderItemList) {
        this.customer = customer;
        this.shippingAddress = shippingAddress;
        this.orderItemList = orderItemList;
        this.total = orderItemList.stream().mapToDouble(OrderItem::getSubtotal).sum();
        orderItemList.forEach(orderItem -> orderItem.setOrder(this));
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", customer=" + customer.getId() + ", shippingAddress=" + shippingAddress
                + ", total=" + total + "]";
    }

}
