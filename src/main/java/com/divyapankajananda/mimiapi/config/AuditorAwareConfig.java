package com.divyapankajananda.mimiapi.config;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.divyapankajananda.mimiapi.aware.SpringSecurityAuditorAware;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditorAwareConfig {
    @Bean
    public AuditorAware<UUID> auditorProvider() {
        return new SpringSecurityAuditorAware();
    }
}