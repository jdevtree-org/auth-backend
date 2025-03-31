package com.jdevtree.auth.backend.service.github.service;

import com.jdevtree.auth.backend.service.github.model.GitHubUserResponse;

public interface GitHubOAuthService {
    GitHubUserResponse authenticateWithGitHub(String code, String redirectUri);
}
