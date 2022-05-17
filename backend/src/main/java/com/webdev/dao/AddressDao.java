package com.webdev.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.webdev.model.Address;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDao {
   @Autowired
   private EntityManager entityManager;

    public Address add(Address address) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        currentSession.save(address);
        currentSession.getTransaction().commit();
        currentSession.close();
        return address;
    }

    public Optional<Address> get(Integer id) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        Optional<Address> address = Optional.ofNullable(currentSession.get(Address.class, id));
        currentSession.getTransaction().commit();
        currentSession.close();
        return address;
    }

    public List<Address> getAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        List<Address> addresses = currentSession.createQuery("from Address", Address.class).list();

        currentSession.getTransaction().commit();
        currentSession.close();

        return addresses;
    }

    public Address update(Address address) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();

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

        currentSession.getTransaction().commit();
        currentSession.close();

        return addressToUpdate;
    }

    public void delete(Integer id) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        Address address = currentSession.get(Address.class, id);
        currentSession.delete(address);
        currentSession.getTransaction().commit();
        currentSession.close();
    }

    public void delete(Address address) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        currentSession.delete(address);
        currentSession.getTransaction().commit();
        currentSession.close();
    }

}
