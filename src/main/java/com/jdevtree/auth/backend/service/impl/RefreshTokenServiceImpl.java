package com.jdevtree.auth.backend.service.impl;

import com.jdevtree.auth.backend.exception.InvalidRefreshTokenException;
import com.jdevtree.auth.backend.service.RefreshTokenService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final Map<String, UUID> refreshTokenStore = new ConcurrentHashMap<>();
    private final SecureRandom secureRandom = new SecureRandom();

    private final StringRedisTemplate redisTemplate;

    public RefreshTokenServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String createRefreshToken(UUID userId) {
        String token = UUID.randomUUID() + "-" + secureRandom.nextInt();
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(token, userId.toString(), Duration.ofDays(7)); // Set expiry to 7 days

        refreshTokenStore.put(token, userId);
        return token;
    }

    @Override
    public UUID verifyRefreshToken(String token) {
        String userId = redisTemplate.opsForValue().get(token);
        if (userId == null) {
            throw new InvalidRefreshTokenException("Refresh token is invalid or expired.");
        }
        return UUID.fromString(userId);
    }

    @Override
    public void invalidateRefreshToken(String token) {
        redisTemplate.delete(token);
    }
}
