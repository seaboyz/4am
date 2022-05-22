package com.webdev.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Entity()
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Integer id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Username is required")
    private @Getter @Setter  String username;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email is required")
    private @Getter @Setter  String email;

    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    private @Getter @Setter  String password;

    @Column(name = "phone_number")
    private @Getter @Setter  String phoneNumber;

    // when save the customer, if there are any unsaved addresses, save them
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private @Getter @Setter  List<Address> addresses = new ArrayList<Address>();

    @OneToMany(mappedBy = "customer")
    private @Getter @Setter  Set<CartItem> cartItems = new HashSet<CartItem>();

    @OneToMany(mappedBy = "customer")
    private @Getter @Setter  Set<Order> orders = new HashSet<Order>();

    public Customer() {
    }

    public Customer(String username, String email, String password, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
                + ", phoneNumber=" + phoneNumber + "]";
    }

}
