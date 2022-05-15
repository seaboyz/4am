package com.webdev.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import com.webdev.model.Address;
import com.webdev.model.CartItem;
import com.webdev.model.Customer;
import com.webdev.model.Order;
import com.webdev.model.OrderItem;
import com.webdev.model.Product;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

// progratic configure hibernate session factory and session
public class HibernateTestUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        try {
            Properties properties = getProperties();
            Configuration configuration = new Configuration();
            configuration.setProperties(properties);
            configuration.addAnnotatedClass(Customer.class);
            configuration.addAnnotatedClass(Address.class);
            configuration.addAnnotatedClass(Order.class);
            configuration.addAnnotatedClass(Product.class);
            configuration.addAnnotatedClass(OrderItem.class);
            configuration.addAnnotatedClass(CartItem.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            return sessionFactory;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sessionFactory;
    }

    private static Properties getProperties() throws IOException {
        // load a properties file for testing purpose
        // using h2 database
        Properties properties = new Properties();
        URL propertiesURL = Thread.currentThread()
                .getContextClassLoader()
                .getResource("hibernateTest.properties");
        try (FileInputStream inputStream = new FileInputStream(propertiesURL.getFile())) {
            properties.load(inputStream);
        }
        return properties;
    }
}