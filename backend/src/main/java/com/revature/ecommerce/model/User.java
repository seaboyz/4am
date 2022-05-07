package com.revature.ecommerce.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity
// singular table name
// @Table(name = "[user]")
// plural table name
@Table(name = "users")

public @NoArgsConstructor // default constructor
@RequiredArgsConstructor // constructor with @NonNull parameters
@Getter @Setter // getters and setters for all fields
class User {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // use traditional generator
    private int id;

    private @NonNull String name;

    private @NonNull String email;

    private @NonNull String password;

    @Column(name = "phone_number")
    private @NonNull String phoneNumber;

    @OneToMany(mappedBy = "user")
    private List<ShippingAddress> addresses;

    public void addAddress(ShippingAddress address) {
        this.addresses.add(address);
        address.setUser(this);

    }
}
