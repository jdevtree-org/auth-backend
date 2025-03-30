package com.jdevtree.auth.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/health").permitAll() // Allow unauthenticated access
                        .anyRequest().authenticated()                // Everything else requires auth
                )
                .oauth2Login(Customizer.withDefaults()); // Enables GitHub login

        return http.build();
    }
}