package com.webdev.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.webdev.model.Address;
import com.webdev.model.Customer;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AddressDao {

    private final EntityManager entityManager;

    // with spring stereotype annotation, we can implicily inject the entity manager(without @Autowired)
    // with @RequiredArgsConstructor,we can autogenarate contructor with the final fields
    // @Autowired
    // public CustomerDao(EntityManager entityManager) {
    //     this.entityManager = entityManager;
    // }

    public Address add(Address address) {
        entityManager.unwrap(Session.class).save(address);
        return address;
    }

    public Address get(Integer id) {
        return entityManager.unwrap(Session.class).get(Address.class, id);
    }

    public List<Address> getAll() {
        return entityManager.unwrap(Session.class).createQuery("from Address", Address.class).list();

    }

    public Address update(Address address) {

        Address addressToUpdate = get(address.getId());

        if (addressToUpdate != null) {
            addressToUpdate.setFirstName(address.getFirstName());
            addressToUpdate.setLastName(address.getLastName());
            addressToUpdate.setStreet(address.getStreet());
            addressToUpdate.setStreet2(address.getStreet2());
            addressToUpdate.setCity(address.getCity());
            addressToUpdate.setState(address.getState());
            addressToUpdate.setZip(address.getZip());
            addressToUpdate.setCountry(address.getCountry());
        }

        Customer customer = address.getCustomer();

        Session currentSession = entityManager.unwrap(Session.class);

        currentSession.saveOrUpdate(customer);
        currentSession.saveOrUpdate(address);

        return addressToUpdate;

    }

    public Integer delete(Integer id) {
        Address address = get(id);
        entityManager.unwrap(Session.class).delete(address);
        return id;
    }

    public void delete(Address address) {
        Customer customer = address.getCustomer();

        List<Address> addresses = customer.getAddresses();

        addresses.remove(address);

        entityManager.unwrap(Session.class).saveOrUpdate(customer);

    }

}
