package com.codeit.demo;

import com.codeit.demo.storage.BinaryContentStorage;
import com.codeit.demo.storage.LocalBinaryContentStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HrbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrbankApplication.class, args);
	}

	@Bean
	public BinaryContentStorage binaryContentStorage() {
		return new LocalBinaryContentStorage();
	}
}
