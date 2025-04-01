package com.jdevtree.auth.backend.service.impl;

import com.jdevtree.auth.backend.exception.InvalidRefreshTokenException;
import com.jdevtree.auth.backend.service.RefreshTokenService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final Map<String, UUID> refreshTokenStore = new ConcurrentHashMap<>();
    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public String createRefreshToken(UUID userId) {
        String token = UUID.randomUUID() + "-" + secureRandom.nextInt();
        refreshTokenStore.put(token, userId);
        return token;
    }

    @Override
    public UUID verifyRefreshToken(String token) {
        UUID userId = refreshTokenStore.get(token);

        if (userId == null) {
            throw new InvalidRefreshTokenException("Refresh token is invalid or expired.");
        }

        return userId;
    }

    @Override
    public void invalidateRefreshToken(String token) {
        refreshTokenStore.remove(token);
    }
}
