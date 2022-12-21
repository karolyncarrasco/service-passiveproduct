package com.bootcamp.passiveProduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient

public class PassiveProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(PassiveProductApplication.class, args);
	}

}
