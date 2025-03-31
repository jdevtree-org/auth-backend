package com.jdevtree.auth.backend.service.github;

import com.jdevtree.auth.backend.exception.OAuthAuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class GitHubOAuthServiceImpl implements GitHubOAuthService {

    private final GitHubOAuthClient gitHubOAuthClient;

    public GitHubOAuthServiceImpl(GitHubOAuthClient gitHubOAuthClient) {
        this.gitHubOAuthClient = gitHubOAuthClient;
    }

    @Override
    public GitHubUserResponse authenticateWithGitHub(String code, String redirectUri) {
        GitHubTokenResponse tokenResponse = gitHubOAuthClient.exchangeCodeForAccessToken(code, redirectUri);

        if (tokenResponse == null || tokenResponse.getAccessToken() == null) {
            throw new OAuthAuthenticationException("Failed to exchange code for access token");
        }

        GitHubUserResponse userResponse = gitHubOAuthClient.fetchGitHubUser(tokenResponse.getAccessToken());

        if (userResponse == null || userResponse.getEmail() == null) {
            throw new OAuthAuthenticationException("Failed to fetch user info or missing email");
        }

        return userResponse;
    }
}
