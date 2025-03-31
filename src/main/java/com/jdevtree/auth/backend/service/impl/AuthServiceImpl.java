package com.jdevtree.auth.backend.service.impl;

import com.jdevtree.auth.backend.api.common.UserView;
import com.jdevtree.auth.backend.api.response.AuthResponse;
import com.jdevtree.auth.backend.dto.AuthResultDto;
import com.jdevtree.auth.backend.exception.OAuthAuthenticationException;
import com.jdevtree.auth.backend.service.AuthService;
import com.jdevtree.auth.backend.service.github.GitHubOAuthService;
import com.jdevtree.auth.backend.service.github.GitHubUserResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final GitHubOAuthService gitHubOAuthService;
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;

    @Override
    public AuthResultDto loginWithGithub(String code, String redirectUri) {
        // Step 1: Authenticate with GitHub
        GitHubUserResponse githubUser = gitHubOAuthService.authenticateWithGitHub(code, redirectUri);

        // Step 2: Lookup user by GitHub ID or email
        Optional<User> existingUser = userRepository.findByGithubId(githubUser.getId());

        User user = existingUser.orElseGet(() -> {
            // Fallback: try by email if GitHub ID not mapped
            return userRepository.findByEmail(githubUser.getEmail())
                    .orElseGet(() -> {
                        // Step 3: Create new user
                        User newUser = new User();
                        newUser.setGithubId(githubUser.getId());
                        newUser.setEmail(githubUser.getEmail());
                        newUser.setName(githubUser.getName());
                        newUser.setAvatar(githubUser.getAvatarUrl());
                        newUser.setProvider(AuthProvider.GITHUB);
                        newUser.setStatus(AccountStatus.ACTIVE);
                        return userRepository.save(newUser);
                    });
        });

        // Step 4: Check user status
        if (user.getStatus() != AccountStatus.ACTIVE) {
            throw new OAuthAuthenticationException("User account is not active");
        }

        // Step 5: Generate JWT
        String jwt = jwtTokenService.generateToken(user);

        // Step 6: Build response
        return AuthResponse.builder()
                .accessToken(jwt)
                .tokenType("Bearer")
                .expiresIn(900)
                .user(new UserView(user.getId(), user.getEmail(), user.getName(), user.getAvatar()))
                .build();
    }
}
