package com.revature.ecommerce;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);

		// configure the database
		Configuration config = new Configuration();

		// if no configuration file is found, use the default
		// config.configure("hibernate.cfg.xml");
		config.configure();

		// build the session factory
		SessionFactory sessionFactory = config.buildSessionFactory();
		// get the session
		Session session = sessionFactory.openSession();
		// start a transaction
		session.beginTransaction();
		System.out.println(session.isConnected());
		// close the session
		session.close();
		System.out.println(session.isConnected());
	}

}
