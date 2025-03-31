package com.jdevtree.auth.backend.service.github;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/*
* Refer to the documentation by GitHub for OAuth
* https://docs.github.com/en/apps/oauth-apps/building-oauth-apps/authorizing-oauth-apps
* */
@Component
public class GitHubOAuthClient {

    private final WebClient webClient;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.base-url}")
    private String githubBaseUrl;

    @Value("${github.api-url}")
    private String githubApiUrl;

    @Value("${github.oauth.token-uri}")
    private String tokenUri;

    @Value("${github.oauth.user-uri}")
    private String userUri;


    public GitHubOAuthClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl(githubBaseUrl).build();
    }

    public GitHubTokenResponse exchangeCodeForAccessToken(String code, String redirectUrl) {
        return webClient.post()
                .uri(tokenUri)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromFormData("client_id", clientId)
                        .with("client_secret", clientSecret)
                        .with("code", code)
                        .with("redirectUrl", redirectUrl))
                .retrieve()
                .bodyToMono(GitHubTokenResponse.class)
                .block();
    }

    public GitHubUserResponse fetchGitHubUser(String accessToken) {
        return WebClient.builder()
                .baseUrl(githubApiUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json")
                .build()
                .get()
                .uri("/user")
                .retrieve()
                .bodyToMono(GitHubUserResponse.class)
                .block();
    }
}
