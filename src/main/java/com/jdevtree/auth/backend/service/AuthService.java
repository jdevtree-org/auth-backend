package com.jdevtree.auth.backend.service;

import com.jdevtree.auth.backend.dto.AuthResultDto;

public interface AuthService {
    AuthResultDto loginWithGithub(String code, String redirectUri);

    AuthResultDto refreshToken(String refreshToken);

    void logout(String refreshToken, String userId);
}
