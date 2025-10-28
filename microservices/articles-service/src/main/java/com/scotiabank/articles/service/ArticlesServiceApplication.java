package com.scotiabank.articles.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ArticlesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArticlesServiceApplication.class, args);
	}

}
