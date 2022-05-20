package com.webdev.dao;

import com.webdev.model.Address;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDao {
    @Autowired
    SessionFactory sessionFactory;

    public Address add(Address address) {
        sessionFactory.getCurrentSession().save(address);
        return address;
    }

    public Address get(Integer id) {
        return sessionFactory.getCurrentSession().get(Address.class, id);

    }

    public Address update(Address address) {
        Session currentSession = sessionFactory.getCurrentSession();

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
        sessionFactory.getCurrentSession().delete(get(id));

    }

    public void delete(Address address) {
        sessionFactory.getCurrentSession().delete(address);
    }

}
