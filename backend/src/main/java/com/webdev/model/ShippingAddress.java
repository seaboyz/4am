package com.webdev.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ShippingAddress {
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_street_line")
    private String street;

    @Column(name = "second_street_line")
    private String street2;

    private String city;

    private String state;

    private String zip;

    private String country;

    public ShippingAddress() {
    }

    public ShippingAddress(String firstName, String lastName, String street, String street2, String city, String state,
            String zip, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStreet() {
        return street;
    }

    public String getStreet2() {
        return street2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "Address [street=" + street + ", street2=" + street2 + ", city=" + city + ", state="
                + state + ", zip=" + zip + ", country=" + country + "]";
    }
}
