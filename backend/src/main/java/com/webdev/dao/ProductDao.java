package com.webdev.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import com.webdev.model.Product;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductDao {

    private final EntityManager entityManager;

    public Product add(Product product) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        currentSession.save(product);
        return product;
    }

    public Product get(Integer id) throws EntityNotFoundException {
        Session currentSession = entityManager.unwrap(Session.class);
        return currentSession.get(Product.class, id);
    }

    public List<Product> getAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        return currentSession.createQuery("from Product", Product.class).list();
    }

    public Product update(Product product) {
        Session currentSession = entityManager.unwrap(Session.class);
        return (Product) currentSession.merge(product);

    }

    public void delete(Integer id) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.delete(get(id));
    }

    public void delete(Product product) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.delete(product);
    }
}
