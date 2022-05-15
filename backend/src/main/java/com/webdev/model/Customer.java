package com.webdev.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity()
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    // when save the customer, if there are any unsaved addresses, save them
    @OneToMany(mappedBy = "customer")
    private Set<Address> addresses = new HashSet<Address>();

    public Set<Address> getAddresses() {
        return addresses;
    }

    // public void addAddress(Address address) {
    // this.addresses.add(address);
    // address.setCustomer(this);
    // }

    @OneToMany(mappedBy = "customer")
    private Set<CartItem> cartItems = new HashSet<CartItem>();

    public void addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
        cartItem.setCustomer(this);
    }

    @OneToMany(mappedBy = "customer")
    private Set<Order> orders = new HashSet<Order>();

    public Customer() {
    }

    public Customer(String username, String email, String password, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
                + ", phoneNumber=" + phoneNumber + "]";
    }

}
