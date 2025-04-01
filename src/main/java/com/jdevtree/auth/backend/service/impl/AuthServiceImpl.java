package com.jdevtree.auth.backend.service.impl;

import com.jdevtree.auth.backend.dto.AuthResultDto;
import com.jdevtree.auth.backend.dto.UserDto;
import com.jdevtree.auth.backend.entity.User;
import com.jdevtree.auth.backend.enums.AccountStatusEnum;
import com.jdevtree.auth.backend.enums.AuthProviderEnum;
import com.jdevtree.auth.backend.exception.OAuthAuthenticationException;
import com.jdevtree.auth.backend.repository.UserRepository;
import com.jdevtree.auth.backend.service.AuthService;
import com.jdevtree.auth.backend.service.JwtTokenService;
import com.jdevtree.auth.backend.service.RefreshTokenService;
import com.jdevtree.auth.backend.service.github.service.GitHubOAuthService;
import com.jdevtree.auth.backend.service.github.model.GitHubUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final GitHubOAuthService gitHubOAuthService;
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public AuthResultDto loginWithGithub(String code, String redirectUri) {
        // Step 1: Authenticate with GitHub
        GitHubUserResponse githubUser = gitHubOAuthService.authenticateWithGitHub(code, redirectUri);

        String userId = String.valueOf(githubUser.getId());

        // Step 2: Lookup user by GitHub ID or email
        User user = userRepository.findByGithubId(String.valueOf(userId))
                .or(() -> userRepository.findByEmail(githubUser.getEmail()))
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .id(UUID.randomUUID())
                            .email(githubUser.getEmail())
                            .name(githubUser.getName())
                            .avatar(githubUser.getAvatarUrl())
                            .githubId(String.valueOf(userId))
                            .status(AccountStatusEnum.ACTIVE)
                            .provider(AuthProviderEnum.GITHUB)
                            .build();
                    return userRepository.save(newUser);
                });

        UserDto userDto = UserDto.builder()
                .name(user.getName())
                .id(user.getId())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .provider(user.getProvider())
                .build();

        // Step 4: Check user status
        if (user.getStatus() != AccountStatusEnum.ACTIVE) {
            throw new OAuthAuthenticationException("User account is not active");
        }

        // Step 5: Generate JWT
        String jwt = jwtTokenService.generateToken(userDto);

        // Step 6: Generate Refresh tokens
        String refreshToken = refreshTokenService.createRefreshToken(user.getId());

        // Step 7: Build dto
        return AuthResultDto.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken) // TODO
                .expiresIn(900)
                .user(userDto)
                .build();
    }

    @Override
    public AuthResultDto refreshToken(String refreshToken) {
        UUID userId = refreshTokenService.verifyRefreshToken(refreshToken);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new OAuthAuthenticationException("User not found"));

        // Invalidate old token
        refreshTokenService.invalidateRefreshToken(refreshToken);

        // Convert to UserDto
        UserDto userDto = UserDto.from(user);
        String newAccessToken = jwtTokenService.generateToken(userDto);
        String newRefreshToken = refreshTokenService.createRefreshToken(userId);

        return AuthResultDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .expiresIn(jwtTokenService.getAccessTokenExpirySeconds())
                .user(userDto)
                .build();
    }

}
