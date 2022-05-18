package com.webdev.utils;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
	public static SessionFactory getSessionFactory() {
		File file = new File("backend/src/main/resources/hibernate.postgres/hibernate.cfg.xml");
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure(file).build();

		SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

		return sessionFactory;
	}

}
