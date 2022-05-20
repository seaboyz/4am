package com.webdev.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.webdev.model.Address;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AddressDao {

    private final EntityManager entityManager;

    public Address add(Address address) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.save(address);
        return address;
    }

    public Optional<Address> get(Integer id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Optional<Address> address = Optional.ofNullable(currentSession.get(Address.class, id));
        return address;
    }

    public List<Address> getAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        List<Address> addresses = currentSession.createQuery("from Address", Address.class).list();

        return addresses;
    }

    public Address update(Address address) {
        Session currentSession = entityManager.unwrap(Session.class);

        Address addressToUpdate = currentSession.get(Address.class, address.getId());

        currentSession.evict(addressToUpdate);

        addressToUpdate.setFirstName(address.getFirstName());
        addressToUpdate.setLastName(address.getLastName());
        addressToUpdate.setStreet(address.getStreet());
        addressToUpdate.setStreet2(address.getStreet2());
        addressToUpdate.setCity(address.getCity());
        addressToUpdate.setState(address.getState());
        addressToUpdate.setZip(address.getZip());
        addressToUpdate.setCountry(address.getCountry());

        currentSession.merge(addressToUpdate);

        return addressToUpdate;
    }

    public void delete(Integer id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Address address = currentSession.get(Address.class, id);
        currentSession.delete(address);
    }

    public void delete(Address address) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.delete(address);
    }

}
