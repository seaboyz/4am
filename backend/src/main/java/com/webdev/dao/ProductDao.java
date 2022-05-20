package com.webdev.dao;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.webdev.model.Product;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProductDao {
    @Autowired
    private SessionFactory sessionFactory;;

    @Transactional
    public Product add(Product product) {
        sessionFactory.getCurrentSession().save(product);
        return product;
    }

    @Transactional(readOnly = true)
    public Product get(Integer id) throws EntityNotFoundException {
        return sessionFactory.getCurrentSession().get(Product.class, id);
    }

    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from Product", Product.class).list();
    }

    public Product update(Product product) {
        return (Product) sessionFactory.getCurrentSession().merge(product);

    }

    @Transactional
    public void delete(Integer id) {
        sessionFactory.getCurrentSession().delete(get(id));
    }

    @Transactional
    public void delete(Product product) {
        sessionFactory.getCurrentSession().delete(product);
    }
}
