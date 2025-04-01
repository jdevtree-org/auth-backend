package com.jdevtree.auth.backend.service;

import com.jdevtree.auth.backend.dto.UserDto;

public interface JwtTokenService {
    String generateToken(UserDto user);

    boolean isValid(String token);

    UserDto extractUser(String token);

    long getExpirationTime();

    int getAccessTokenExpirySeconds();
}
