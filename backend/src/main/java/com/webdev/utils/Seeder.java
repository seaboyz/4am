package com.webdev.utils;

import java.io.File;
import java.io.FileReader;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webdev.model.Customer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Seeder {
    public static void main(String[] args) {
        loadCustomer();
    }

    public static void loadCustomer() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        // clear the database
        session.createQuery("delete from Customer").executeUpdate();

        try {
            File file = new File("backend/src/main/resources/data/users.json");
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
}
