package com.jdevtree.auth.backend.service;

import com.jdevtree.auth.backend.dto.AuthResultDto;

public interface AuthService {
    AuthResultDto loginWithGithub(String code, String redirectUri);
}
