package com.example.ufo_fi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class UfoFiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UfoFiApplication.class, args);
	}

}
