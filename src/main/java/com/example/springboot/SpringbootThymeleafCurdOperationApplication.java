package com.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class SpringbootThymeleafCurdOperationApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootThymeleafCurdOperationApplication.class, args);
	}

}

