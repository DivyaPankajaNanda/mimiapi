package com.divyapankajananda.mimiapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition
public class OpenAPIConfig {
    
	@Bean
	public OpenAPI openAPI(){
		return new OpenAPI()
			.info(new Info().title("Mimiapi Docs").version("0.1.0"))
			.components(new Components()
					.addSecuritySchemes("bearer-key", new SecurityScheme()
							.type(SecurityScheme.Type.HTTP)
							.scheme("bearer")
							.bearerFormat("JWT")
							.in(SecurityScheme.In.HEADER)
							.name("Authorization"))
			)
			.addSecurityItem(new SecurityRequirement().addList("bearer-key"));
    }
}
