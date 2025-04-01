package com.jdevtree.auth.backend.service;


import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {
    /**
     * Generates and stores a new refresh token for the given userId.
     * Returns the generated token as a string.
     */
    String createRefreshToken(UUID userId);

    /**
     * Verifies that the given token is valid and associated with a known user.
     * Returns the userId if valid, otherwise empty.
     */
    UUID verifyRefreshToken(String token);

    /**
     * Invalidates a refresh token (e.g., after successful use or logout).
     */
    void invalidateRefreshToken(String token);
}
