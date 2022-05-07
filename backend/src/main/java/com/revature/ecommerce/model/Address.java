package com.revature.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter // getters and setters for all fields
@NoArgsConstructor // default constructor
@AllArgsConstructor // constructor with @NonNull parameters
public class Address {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // use traditional generator
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name")
    private @NonNull String name;

    @Column(name = "phone_number")
    private @NonNull String phone;

    @Column(name = "first_street_line")
    @NonNull
    private String street;

    @Column(name = "second_street_line")
    private String street2;

    @Column(name = "city")
    @NonNull
    private String city;

    @Column(name = "state")
    @NonNull
    private String state;

    @Column(name = "zip")
    @NonNull
    private String zip;

    @Column(name = "country")
    @NonNull
    private String country;
}
