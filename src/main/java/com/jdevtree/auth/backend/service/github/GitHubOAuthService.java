package com.jdevtree.auth.backend.service.github;

public interface GitHubOAuthService {
    GitHubUserResponse authenticateWithGitHub(String code, String redirectUri);
}
