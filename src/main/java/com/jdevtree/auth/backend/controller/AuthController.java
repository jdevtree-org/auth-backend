package com.jdevtree.auth.backend.controller;

import com.jdevtree.auth.backend.api.common.UserView;
import com.jdevtree.auth.backend.api.request.AuthRequest;
import com.jdevtree.auth.backend.api.response.AuthResponse;
import com.jdevtree.auth.backend.dto.AuthResultDto;
import com.jdevtree.auth.backend.dto.UserDto;
import com.jdevtree.auth.backend.enums.AuthResponseCodeEnum;
import com.jdevtree.auth.backend.service.AuthService;
import com.jdevtree.auth.backend.vo.ResponseBean;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor // Automatically generates constructor for all final fields
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/health")
    public String health() {
        return "Endpoint is healthy";
    }

    @PostMapping("/oauth/github")
    public ResponseBean<AuthResponse> githubLogin(@Valid @RequestBody AuthRequest request) {
        AuthResultDto result = authService.loginWithGithub(request.getCode(), request.getRedirectUrl());

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

        return new ResponseBean<>(AuthResponseCodeEnum.SUCCESS.name(), AuthResponseCodeEnum.SUCCESS.getMessage(), response);
    }
}
