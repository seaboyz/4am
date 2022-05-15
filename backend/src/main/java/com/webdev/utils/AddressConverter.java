package com.webdev.utils;

import com.webdev.model.Address;
import com.webdev.model.ShippingAddress;

public class AddressConverter {
    public static ShippingAddress addressToShippingAddress(String firstName, String lastName,
            com.webdev.model.Address address) {
        return new ShippingAddress(
                firstName,
                lastName,
                address.getStreet(),
                address.getStreet2(),
                address.getCity(),
                address.getState(),
                address.getZip(),
                address.getCountry());

    }

    public static Address shippingAddressToAddress(ShippingAddress address) {
        return new Address(
                address.getFirstName(),
                address.getLastName(),
                address.getStreet(),
                address.getStreet2(),
                address.getCity(),
                address.getState(),
                address.getZip(),
                address.getCountry());
    }
}
