package com.webdev.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class ShippingAddress {
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
    @Column(name = "country", nullable = false)
    private String country;

}
