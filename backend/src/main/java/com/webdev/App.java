package com.webdev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Right now, spring-boot knows nothing about hibernate
@SpringBootApplication
@RestController
public class App {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(App.class, args);

		context.start();

		// CustomerDao customerDao = context.getBean(CustomerDao.class);

		// Customer customer = new Customer(
		// 		"johnd",
		// 		"john@gmail.com",
		// 		"m38rmF$",
		// 		"1-570-236-7033");

		// customerDao.add(customer);

	}

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

}