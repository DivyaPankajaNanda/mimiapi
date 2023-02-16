package com.divyapankajananda.mimiapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class OpenAPIConfig {
    
	@Bean
	public OpenAPI openAPI(){
		return new OpenAPI().info(new Info().title("Mimiapi Docs").version("0.1.0"));
	}
}
