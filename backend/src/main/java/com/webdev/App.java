package com.webdev;

import java.util.List;

import com.google.gson.Gson;
import com.webdev.model.Customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Right now, spring-boot knows nothing about hibernate
@SpringBootApplication
@RestController
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@RequestMapping("/")
	public String index() {
		Customer customer = new Customer(
				"johnd",
				"john@gmail.com",
				"m38rmF$",
				"-570-236-7033");

		Gson gson = new Gson();
		return gson.toJson(customer, Customer.class);
	}

}