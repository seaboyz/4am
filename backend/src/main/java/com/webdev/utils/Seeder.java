package com.webdev.utils;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.List;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.webdev.model.Customer;

public class Seeder {

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        // get all customers
        List<Customer> customers = session.createQuery("from Customer", Customer.class).getResultList();

        

        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.setPrettyPrinting();

        Gson gson = gsonBuilder.create();

        String json = gson.toJson(customers);

        System.out.println(json);

        session.close();
        sessionFactory.close();

    }

    public static void loadCustomerFromFile() {
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

}
