package com.jdevtree.auth.backend.service.github.client;

import com.jdevtree.auth.backend.service.github.model.GitHubEmailResponse;
import com.jdevtree.auth.backend.service.github.model.GitHubTokenResponse;
import com.jdevtree.auth.backend.service.github.model.GitHubUserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

/*
* Refer to the documentation by GitHub for OAuth
* https://docs.github.com/en/apps/oauth-apps/building-oauth-apps/authorizing-oauth-apps
* */
@Component
public class GitHubOAuthClient {

    private final WebClient webClient;

    private final String clientId;

    private final String clientSecret;

    private final String githubBaseUrl;

    private final String githubApiUrl;

    private final String tokenUri;

    private final String userUri;


    public GitHubOAuthClient(
            @Value("${github.client.id}") String clientId,
            @Value("${github.client.secret}") String clientSecret,
            @Value("${github.base-url}") String githubBaseUrl,
            @Value("${github.api-url}") String githubApiUrl,
            @Value("${github.oauth.token-uri}") String tokenUri,
            @Value("${github.oauth.user-uri}") String userUri,
            WebClient.Builder builder
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.githubBaseUrl = githubBaseUrl;
        this.githubApiUrl = githubApiUrl;
        this.tokenUri = tokenUri;
        this.userUri = userUri;
        this.webClient = builder.baseUrl(githubBaseUrl).build();
    }

    public GitHubTokenResponse exchangeCodeForAccessToken(String code, String redirectUrl) {
        return webClient.post()
                .uri(tokenUri)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromFormData("client_id", clientId)
                        .with("client_secret", clientSecret)
                        .with("code", code)
                        .with("redirect_uri", redirectUrl))
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
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .doOnNext(body -> System.out.println("GitHub /user/emails error body: " + body))
                                .then(Mono.error(new RuntimeException("GitHub email fetch failed")))
                )
                .bodyToMono(GitHubUserResponse.class)
                .block();
    }

    public String fetchPrimaryVerifiedEmail(String accessToken) {
        System.out.println("Calling /user/emails with token: " + accessToken);

        List<GitHubEmailResponse> emails = WebClient.builder()
                .baseUrl(githubApiUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "token " + accessToken)
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json")
                .defaultHeader(HttpHeaders.USER_AGENT, "jdevtree-app")
                .build()
                .get()
                .uri("/user/emails")
                .retrieve()
                .bodyToFlux(GitHubEmailResponse.class)
                .collectList()
                .block();

        if (emails != null) {
            return emails.stream()
                    .filter(email -> Boolean.TRUE.equals(email.isPrimary()) && Boolean.TRUE.equals(email.isVerified()))
                    .map(GitHubEmailResponse::getEmail)
                    .findFirst()
                    .orElse(null);
        }
        return null;

    }

}
