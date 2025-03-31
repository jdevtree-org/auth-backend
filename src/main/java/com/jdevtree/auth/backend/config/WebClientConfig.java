package com.jdevtree.auth.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class that defines a reusable WebClient builder.

 * This builder is used for making HTTP calls to external services (e.g., GitHub APIs).
 * We define it as a @Bean so Spring can inject it wherever needed.

 * Usage:
 * - Inject WebClient.Builder in any service that needs to make HTTP requests
 * - Allows customizing WebClient per external service (e.g., setting base URL, headers)
 */
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
