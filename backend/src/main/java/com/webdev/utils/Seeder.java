package com.webdev.utils;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webdev.model.Customer;
import com.webdev.model.Product;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Seeder {

    public static void main(String[] args) {

        loadCustomerFromFile();
        loadProductsFromFile();

    }

    public static void loadCustomerFromFile() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        // clear the database
        session.createQuery("delete from Customer").executeUpdate();

        try {
            File file = new File("src/main/resources/data/users.json");
            FileReader fileReader = new FileReader(file);

            GsonBuilder gsonBuilder = new GsonBuilder();

            // exclude the id field
            ExclusionStrategy excludeId = new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getName().equals("id");
                }

                @Override
                public boolean shouldSkipClass(java.lang.Class<?> clazz) {
                    return false;
                }
            };

            // convert fildname phone to phoneNumber
            FieldNamingStrategy phoneToPhoneNumber = new FieldNamingStrategy() {
                @Override
                public String translateName(Field f) {
                    if (f.getName().equals("phoneNumber")) {
                        return "phone";
                    }
                    return f.getName();
                }
            };

            gsonBuilder.setFieldNamingStrategy(phoneToPhoneNumber);

            gsonBuilder.addDeserializationExclusionStrategy(excludeId);

            Gson gson = gsonBuilder.create();

            Customer[] customers = gson.fromJson(fileReader, Customer[].class);
            for (Customer c : customers) {
                System.out.println(c);
                session.save(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    public static void loadProductsFromFile() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from Product").executeUpdate();

        try {
            File file = new File("src/main/resources/data/products.json");
            FileReader fileReader = new FileReader(file);
            GsonBuilder gsonBuilder = new GsonBuilder();

            // exclude the id field
            ExclusionStrategy excludeId = new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getName().equals("id");
                }

                @Override
                public boolean shouldSkipClass(java.lang.Class<?> clazz) {
                    return false;
                }
            };

            gsonBuilder.addDeserializationExclusionStrategy(excludeId);

            Gson gson = gsonBuilder.create();

            Product[] products = gson.fromJson(fileReader, Product[].class);

            for (Product p : products) {
                System.out.println(p);
                session.save(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();

    }

}
