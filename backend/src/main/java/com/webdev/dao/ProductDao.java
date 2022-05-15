package com.webdev.dao;

import java.util.List;
import java.util.Optional;

import com.webdev.model.Product;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ProductDao implements Dao<Product> {
    private SessionFactory sessionFactory;

    public ProductDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Product add(Product product) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();
        session.close();
        return product;
    }

    @Override
    public Optional<Product> get(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Optional<Product> product = Optional.ofNullable(session.get(Product.class, id));
        session.getTransaction().commit();
        session.close();
        return product;
    }

    @Override
    public List<Product> getAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Product> products = session.createQuery("from Product", Product.class).list();

        session.getTransaction().commit();
        session.close();

        return products;
    }

    @Override
    public Product update(Product product) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Product productToUpdate = session.get(Product.class, product.getId());

        session.evict(productToUpdate);

        productToUpdate.setName(product.getName());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setImage(product.getImage());
        productToUpdate.setCategory(product.getCategory());

        session.merge(productToUpdate);

        session.getTransaction().commit();
        session.close();

        return productToUpdate;

    }

    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Product productToDelete = session.get(Product.class, id);
        session.delete(productToDelete);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Product product) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(product);
        session.getTransaction().commit();
        session.close();
    }
}
