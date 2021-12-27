package com.example.servicesgroup3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class}
)
public class ServicesGroup3Application {

	public static void main(String[] args) {
		SpringApplication.run(ServicesGroup3Application.class, args);
	}

}
