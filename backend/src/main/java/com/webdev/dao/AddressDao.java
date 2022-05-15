package com.webdev.dao;

import java.util.List;
import java.util.Optional;

import com.webdev.model.Address;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class AddressDao implements Dao<Address> {
    private SessionFactory sessionFactory;

    public AddressDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Address add(Address address) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(address);
        session.getTransaction().commit();
        session.close();
        return address;
    }

    @Override
    public Optional<Address> get(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Optional<Address> address = Optional.ofNullable(session.get(Address.class, id));
        session.getTransaction().commit();
        session.close();
        return address;
    }

    @Override
    public List<Address> getAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Address> addresses = session.createQuery("from Address", Address.class).list();

        session.getTransaction().commit();
        session.close();

        return addresses;
    }

    @Override
    public Address update(Address address) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Address addressToUpdate = session.get(Address.class, address.getId());

        session.evict(addressToUpdate);

        addressToUpdate.setFirstName(address.getFirstName());
        addressToUpdate.setLastName(address.getLastName());
        addressToUpdate.setStreet(address.getStreet());
        addressToUpdate.setStreet2(address.getStreet2());
        addressToUpdate.setCity(address.getCity());
        addressToUpdate.setState(address.getState());
        addressToUpdate.setZip(address.getZip());
        addressToUpdate.setCountry(address.getCountry());

        session.merge(addressToUpdate);

        session.getTransaction().commit();
        session.close();

        return addressToUpdate;
    }

    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Address address = session.get(Address.class, id);
        session.delete(address);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Address address) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(address);
        session.getTransaction().commit();
        session.close();
    }

}
