package com.webdev.dao;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.webdev.model.Product;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {
    @Autowired
    private SessionFactory sessionFactory;;

    public Product add(Product product) {
        sessionFactory.getCurrentSession().save(product);
        return product;
    }

    public Product get(Integer id) throws EntityNotFoundException {
        return sessionFactory.getCurrentSession().get(Product.class, id);
    }

    public List<Product> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from Product", Product.class).list();
    }

    public Product update(Product product) {
        return (Product) sessionFactory.getCurrentSession().merge(product);

    }

    public void delete(Integer id) {
        sessionFactory.getCurrentSession().delete(get(id));
    }

    public void delete(Product product) {
        sessionFactory.getCurrentSession().delete(product);
    }
}
