package com.webdev.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id // primary key
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // once the order is saved, the order items are saved in the order_items table
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList = new ArrayList<OrderItem>();

    // shipping address
    @Embedded
    private ShippingAddress shippingAddress;

    private double total;

    public Order() {
    }

    // there is no meaning to have an order without a customer
    // there is no meaning to have an order without an order item
    // there is no meaning to have an order without a shipping address
    // total should be calculated from the order items
    public Order(Customer customer, ShippingAddress shippingAddress, List<OrderItem> orderItemList) {
        this.customer = customer;
        this.shippingAddress = shippingAddress;
        this.orderItemList = orderItemList;
        this.total = orderItemList.stream().mapToDouble(OrderItem::getSubtotal).sum();
        orderItemList.forEach(orderItem -> orderItem.setOrder(this));
    }

    public Integer getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItem> getOrderDetails() {
        return orderItemList;
    }

    public double getTotal() {
        return total;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", customer=" + customer + ", shippingAddress=" + shippingAddress
                + ", total=" + total + "]";
    }

}
