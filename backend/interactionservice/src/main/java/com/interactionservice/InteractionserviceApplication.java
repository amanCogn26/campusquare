package com.interactionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InteractionserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InteractionserviceApplication.class, args);
	}

}
