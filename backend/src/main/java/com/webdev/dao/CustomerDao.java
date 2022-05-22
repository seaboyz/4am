package com.webdev.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import com.webdev.model.Address;
import com.webdev.model.Customer;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomerDao {

    private final EntityManager entityManager;

    // with spring stereotype annotation, we can implicily inject the entity manager(without @Autowired)
    // with @RequiredArgsConstructor,we can autogenarate contructor with the final fields
    // @Autowired
    // public CustomerDao(EntityManager entityManager) {
    //     this.entityManager = entityManager;
    // }

    public Customer add(Customer customer) {

        entityManager.unwrap(Session.class).save(customer);
        return customer;
    }

    public Customer get(Integer id) throws EntityNotFoundException {

        return entityManager.unwrap(Session.class).get(Customer.class, id);

    }

    public Customer getbyEmail(String email) throws NoResultException {

        return entityManager.unwrap(Session.class)
                .createQuery("from Customer where email = :email", Customer.class)
                .setParameter("email", email).getSingleResult();

    }

    public List<Customer> getAll() {

        return entityManager.unwrap(Session.class)
                .createQuery("from Customer", Customer.class).list();

    }

    public Customer update(Customer customer) {

        Customer customerToUpdate = entityManager
                .unwrap(Session.class)
                .get(Customer.class, customer.getId());

        customerToUpdate.setUsername(customer.getUsername());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPassword(customer.getPassword());
        customerToUpdate.setPhoneNumber(customer.getPhoneNumber());
        if (customer.getAddresses().size() != customerToUpdate.getAddresses().size()) {
            customerToUpdate.setAddresses(customer.getAddresses());
        }

        return customerToUpdate;
    }

    public void delete(Integer id) {

        entityManager.unwrap(Session.class).unwrap(Session.class).delete(get(id));

    }

    public void delete(Customer customer) {

        entityManager.unwrap(Session.class).delete(customer);
    }

    public Customer addAddress(Customer customer, Address address) {

        Session session = entityManager.unwrap(Session.class);

        customer.getAddresses().add(address);

        address.setCustomer(customer);

        // I don't think we need to save the customer, because the customer is already saved in the address
        // there is no addresses collumn in the customer table
        // session.save(customer);

        session.save(address);

        return customer;
    }
}
