package com.lms.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;

/**
 * Swagger/OpenAPI configuration for the Loan Management System.
 * Defines API metadata, security schemes, and tag documentation for grouped endpoints.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Loan Management System API")
                .description("""
                    This API enables integration between the Loan Management System, 
                    Core Banking System (CBS), and Scoring Engine. It supports KYC verification, 
                    transaction data retrieval, credit scoring, loan application evaluation, 
                    and client registration for score callbacks.
                """)
                .version("1.0"))
            .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
            .components(new Components()
                .addSecuritySchemes("basicAuth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("basic")))
            .tags(List.of(
                new Tag()
                    .name("Core Banking System")
                    .description("Endpoints to retrieve customer KYC and transaction data from the Core Banking System."),
                new Tag()
                    .name("Scoring Engine")
                    .description("Endpoints to initiate scoring and retrieve credit score data for customers."),
                new Tag()
                    .name("Client Registration")
                    .description("Endpoint to register this service with the Scoring Engine for receiving score evaluation callbacks.")
            ));
    }
}
