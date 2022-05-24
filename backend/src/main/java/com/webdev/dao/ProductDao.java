package com.webdev.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import com.webdev.model.Product;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductDao {

    private final EntityManager entityManager;

    @Transactional
    public Product add(Product product) {
        entityManager.unwrap(Session.class).save(product);
        return product;
    }

    @Transactional(readOnly = true)
    public Product get(Integer id) throws EntityNotFoundException {
        return entityManager.unwrap(Session.class).get(Product.class, id);
    }

    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return entityManager.unwrap(Session.class).createQuery("from Product", Product.class).list();
    }

    public Product update(Product product) {
        return (Product) entityManager.unwrap(Session.class).merge(product);

    }

    @Transactional
    public void delete(Integer id) {
        entityManager.unwrap(Session.class).delete(get(id));
    }

    @Transactional
    public void delete(Product product) {
        entityManager.unwrap(Session.class).delete(product);
    }
}
