package com.webdev.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.webdev.model.Product;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {
    @Autowired
    private EntityManager entityManager;

    public Product add(Product product) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        currentSession.save(product);
        currentSession.getTransaction().commit();
        currentSession.close();
        return product;
    }

    public Optional<Product> get(Integer id) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        Optional<Product> product = Optional.ofNullable(currentSession.get(Product.class, id));
        currentSession.getTransaction().commit();
        currentSession.close();
        return product;
    }

    public List<Product> getAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        List<Product> products = currentSession.createQuery("from Product", Product.class).list();

        currentSession.getTransaction().commit();
        currentSession.close();

        return products;
    }

    public Product update(Product product) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();

        Product productToUpdate = currentSession.get(Product.class, product.getId());

        currentSession.evict(productToUpdate);

        productToUpdate.setName(product.getName());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setImage(product.getImage());
        productToUpdate.setCategory(product.getCategory());

        currentSession.merge(productToUpdate);

        currentSession.getTransaction().commit();
        currentSession.close();

        return productToUpdate;

    }

    public void delete(Integer id) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        Product productToDelete = currentSession.get(Product.class, id);
        currentSession.delete(productToDelete);
        currentSession.getTransaction().commit();
        currentSession.close();
    }

    public void delete(Product product) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        currentSession.delete(product);
        currentSession.getTransaction().commit();
        currentSession.close();
    }
}
