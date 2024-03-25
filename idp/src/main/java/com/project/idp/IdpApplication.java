package com.project.idp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class IdpApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdpApplication.class, args);
	}
}
