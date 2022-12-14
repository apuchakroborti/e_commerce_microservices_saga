package com.apu.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProvidentFundApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProvidentFundApplication.class, args);
	}

}
