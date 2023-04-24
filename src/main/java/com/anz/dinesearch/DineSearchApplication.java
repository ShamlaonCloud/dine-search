package com.anz.dinesearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Shamla TK application for food finding
 *
 */
@SpringBootApplication
public class DineSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(DineSearchApplication.class, args);
	}

	@Bean
	public WebClient webClient() {
		return WebClient.builder().baseUrl("https://maps.googleapis.com/maps/api").build();
	}
}
