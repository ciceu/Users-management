package com.users.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.users.demo")
public class UsersManagementApplication {

	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", "application");
		SpringApplication.run(UsersManagementApplication.class, args);
	}
}