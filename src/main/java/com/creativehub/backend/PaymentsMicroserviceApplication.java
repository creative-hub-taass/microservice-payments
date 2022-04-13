package com.creativehub.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class PaymentsMicroserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PaymentsMicroserviceApplication.class, args);
	}
}
