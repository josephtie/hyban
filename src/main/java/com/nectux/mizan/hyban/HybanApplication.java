package com.nectux.mizan.hyban;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@SpringBootApplication

public class HybanApplication {

	public static void main(String[] args) {
		SpringApplication.run(HybanApplication.class, args);
	}

}
