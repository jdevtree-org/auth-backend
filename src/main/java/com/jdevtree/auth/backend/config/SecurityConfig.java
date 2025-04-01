package com.jdevtree.auth.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF for APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/health",
                                "/auth/oauth/github",
                                "/auth/account/login",
                                "/auth/token/refresh",
                                "auth/test/refresh",
                                "/auth/test/invalidate"
                        ).permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }

}