package com.lms.credable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.lms"}) // 🟢 Scan for @Component, @Service, @Repository
@EntityScan(basePackages = {"com.lms.model"}) // 🟢 Scan for @Entity
@EnableJpaRepositories(basePackages = {"com.lms.repository"}) // 🟢 Scan for repositories
public class CredableApplication {
    public static void main(String[] args) {
        SpringApplication.run(CredableApplication.class, args);
    }
}
