package com.webdev.dao;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import com.webdev.model.Address;
import com.webdev.model.Customer;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CustomerDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public Customer add(Customer customer) {
        sessionFactory.getCurrentSession().save(customer);
        return customer;
    }

    @Transactional(readOnly = true)
    public Customer get(Integer id) throws EntityNotFoundException {

        return sessionFactory.getCurrentSession().get(Customer.class, id);

    }

    public Customer getbyEmail(String email) throws NoResultException {

        Customer customerFromDb = sessionFactory.getCurrentSession()
                .createQuery("from Customer where email = :email", Customer.class)
                .setParameter("email", email).getSingleResult();

        return customerFromDb;
    }

    @Transactional(readOnly = true)
    public List<Customer> getAll() {

        List<Customer> customers = sessionFactory.getCurrentSession().createQuery("from Customer", Customer.class)
                .list();
        return customers;
    }

    public Customer update(Customer customer) {

        Customer customerToUpdate = sessionFactory.getCurrentSession().get(Customer.class, customer.getId());

        customerToUpdate.setUsername(customer.getUsername());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPassword(customer.getPassword());
        customerToUpdate.setPhone(customer.getPhone());
        if (customer.getAddresses().size() != customerToUpdate.getAddresses().size()) {
            customerToUpdate.setAddresses(customer.getAddresses());
        }

        return customerToUpdate;
    }

    @Transactional
    public void delete(Integer id) {

        Customer customer = sessionFactory.getCurrentSession().get(Customer.class, id);
        sessionFactory.getCurrentSession().delete(customer);
    }

    public void delete(Customer customer) {

        sessionFactory.getCurrentSession().delete(customer);
    }

    @Transactional
    public Customer addAddress(Customer customer, Address address) {

        // sessionFactory.getCurrentSession().beginTransaction();
        // manually add address to database, so trun off the cascade in the customer entity
        sessionFactory.getCurrentSession().save(address);
        customer.getAddresses().add(address);
        sessionFactory.getCurrentSession().merge(customer);
        sessionFactory.getCurrentSession().getTransaction().commit();

        return customer;
    }
}
