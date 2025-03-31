package com.jdevtree.auth.backend.service.github.service.impl;

import com.jdevtree.auth.backend.exception.OAuthAuthenticationException;
import com.jdevtree.auth.backend.service.github.client.GitHubOAuthClient;
import com.jdevtree.auth.backend.service.github.model.GitHubTokenResponse;
import com.jdevtree.auth.backend.service.github.model.GitHubUserResponse;
import com.jdevtree.auth.backend.service.github.service.GitHubOAuthService;
import org.springframework.stereotype.Service;

@Service
public class GitHubOAuthServiceImpl implements GitHubOAuthService {

    private final GitHubOAuthClient gitHubOAuthClient;

    public GitHubOAuthServiceImpl(GitHubOAuthClient gitHubOAuthClient) {
        this.gitHubOAuthClient = gitHubOAuthClient;
    }

    @Override
    public GitHubUserResponse authenticateWithGitHub(String code, String redirectUri) {
        System.out.println("Exchanging code for Access Token");
        GitHubTokenResponse tokenResponse = gitHubOAuthClient.exchangeCodeForAccessToken(code, redirectUri);

        System.out.println("TokenResponse: " + tokenResponse.toString());

        String accessToken = tokenResponse.getAccess_token();

        if (tokenResponse == null || tokenResponse.getAccess_token() == null) {
            throw new OAuthAuthenticationException("Failed to exchange code for access token");
        }

        GitHubUserResponse userResponse = gitHubOAuthClient.fetchGitHubUser(accessToken);
        System.out.println("Fetching github user: " + userResponse.toString());

        if (userResponse.getEmail() == null) {
            String email = gitHubOAuthClient.fetchPrimaryVerifiedEmail(accessToken);
            userResponse.setEmail(email);
        }
        if (userResponse == null || userResponse.getEmail() == null) {
            throw new OAuthAuthenticationException("Failed to fetch user info or missing email");
        }

        return userResponse;
    }
}
