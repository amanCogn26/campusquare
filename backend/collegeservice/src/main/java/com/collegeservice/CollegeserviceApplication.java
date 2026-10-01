package com.collegeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CollegeserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollegeserviceApplication.class, args);
	}

}
