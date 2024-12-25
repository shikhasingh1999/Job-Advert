package com.example.jobService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer () {
        return web -> web
                    .ignoring()
                    .requestMatchers("/swagger-resources/**",
                        "/swagger-ui.html/**",
                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                    );
    }
}
