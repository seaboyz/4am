package com.webdev.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Embeddable
@RequiredArgsConstructor
@Getter
@Setter
public class ShippingAddress {
    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @Column(name = "last_name")
    private String lastName;

    @NonNull
    @Column(name = "first_street_line")
    private String street;

    @NonNull
    @Column(name = "second_street_line")
    private String street2;

    @NonNull
    private String city;

    @NonNull
    private String state;

    @NonNull
    private String zip;

    @NonNull
    private String country;


   

    
}
