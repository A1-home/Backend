package com.example.Security;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityApplication {

	public static void main(String[] args) {
		// Load environment variables from .env file


		SpringApplication.run(SecurityApplication.class, args);
	}
}
