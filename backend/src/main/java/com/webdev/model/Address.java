package com.webdev.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@RequiredArgsConstructor
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NonNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NonNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NonNull
    @Column(name = "first_street_line", nullable = false)
    private String street;

    @NonNull
    @Column(name = "second_street_line", nullable = false)
    private String street2;

    @NonNull
    @Column(name = "city", nullable = false)
    private String city;

    @NonNull
    @Column(name = "state", nullable = false)
    private String state;

    @NonNull
    @Column(name = "zip", nullable = false)
    private String zip;

    @NonNull
    @Column(name = "phone", nullable = false)
    private String country;

    @Override
    public String toString() {
        return "Address [id=" + id + ", street=" + street + ", street2=" + street2 + ", city=" + city + ", state="
                + state + ", zip=" + zip + ", country=" + country + "]";
    }

}
