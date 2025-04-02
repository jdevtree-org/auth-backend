package com.jdevtree.auth.backend.controller;

import com.jdevtree.auth.backend.api.common.UserView;
import com.jdevtree.auth.backend.api.request.AuthRequest;
import com.jdevtree.auth.backend.api.request.RefreshRequest;
import com.jdevtree.auth.backend.api.request.RefreshTokenRequest;
import com.jdevtree.auth.backend.api.response.AuthResponse;
import com.jdevtree.auth.backend.dto.AuthResultDto;
import com.jdevtree.auth.backend.dto.UserDto;
import com.jdevtree.auth.backend.enums.AuthResponseCodeEnum;
import com.jdevtree.auth.backend.service.AuthService;
import com.jdevtree.auth.backend.service.RefreshTokenService;
import com.jdevtree.auth.backend.vo.ResponseBean;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor // Automatically generates constructor for all final fields
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @GetMapping("/health")
    public String health() {
        return "Endpoint is healthy";
    }

    @PostMapping("/oauth/github")
    public ResponseBean githubLogin(@Valid @RequestBody AuthRequest request) {
        System.out.println("Logging in with Github");
        AuthResultDto result = authService.loginWithGithub(request.code(), request.redirectUrl());

        UserDto user = result.getUser();

        UserView userView = UserView.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .avatar(user.getAvatar())
                .build();

        AuthResponse response = AuthResponse.builder()
                .accessToken(result.getAccessToken())
                .tokenType("Bearer")
                .expiresIn(result.getExpiresIn())
                .refreshToken(result.getRefreshToken())
                .user(userView)
                .build();

        return ResponseBean.success(response);
    }

    @PostMapping("/token/refresh")
    public ResponseBean refresh(@Valid @RequestBody RefreshRequest request) {
        AuthResultDto result = authService.refreshToken(request.refreshToken());

        UserDto user = result.getUser();

        UserView userView = UserView.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .avatar(user.getAvatar())
                .build();


        AuthResponse response = AuthResponse.builder()
                .accessToken(result.getAccessToken())
                .tokenType("Bearer")
                .expiresIn(result.getExpiresIn())
                .user(userView)
                .refreshToken(result.getRefreshToken())
                .build();

        return ResponseBean.success(response);
    }

    @PostMapping("/logout")
    public ResponseBean logout(@RequestBody RefreshTokenRequest request, @AuthenticationPrincipal JwtAuthenticationToken auth) {
        authService.logout(request.getRefreshToken(), auth.getName());
        return ResponseBean.success();
    }


    @PostMapping("/test/refresh")
    public ResponseBean testGenerateRefreshToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        refreshTokenService.verifyRefreshToken(token);
        return ResponseBean.success();
    }

    @PostMapping("/test/invalidate")
    public ResponseBean testInvalidateRefreshToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        refreshTokenService.invalidateRefreshToken(token);
        return ResponseBean.success();
    }
}
