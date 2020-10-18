package com.movies_unlimited;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableJpaAuditing
public class MoviesUnlimitedApplication {

	public static void main(String[] args) {

		SpringApplication.run(MoviesUnlimitedApplication.class, args);
	}

}
