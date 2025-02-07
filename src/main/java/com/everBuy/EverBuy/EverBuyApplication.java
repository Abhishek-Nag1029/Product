package com.everBuy.EverBuy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.everBuy.EverBuy.repository")
public class EverBuyApplication {

	public static void main(String[] args) {
		SpringApplication.run(EverBuyApplication.class, args);
	}

}
