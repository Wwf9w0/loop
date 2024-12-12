package com.loop.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LoopServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoopServiceApplication.class, args);
	}

}
