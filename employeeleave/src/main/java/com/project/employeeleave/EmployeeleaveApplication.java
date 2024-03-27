package com.project.employeeleave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmployeeleaveApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeleaveApplication.class, args);
	}

}
