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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity()
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @NonNull
    @Column(nullable = false, unique = true)
    private String username;

    @NonNull
    @Column(nullable = false, unique = true)
    private String email;

    @NonNull
    @Column(nullable = false)
    private String password;

    @NonNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    // when save the customer, if there are any unsaved addresses, save them
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Address> addresses = new ArrayList<Address>();

    @OneToMany(mappedBy = "customer")
    private Set<CartItem> cartItems = new HashSet<CartItem>();

    @OneToMany(mappedBy = "customer")
    private Set<Order> orders = new HashSet<Order>();

    @Override
    public String toString() {
        return "Customer [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
                + ", phoneNumber=" + phoneNumber + "]";
    }

}
