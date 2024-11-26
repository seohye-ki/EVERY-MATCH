package com.everymatch.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EverymatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(EverymatchApplication.class, args);
	}

}
