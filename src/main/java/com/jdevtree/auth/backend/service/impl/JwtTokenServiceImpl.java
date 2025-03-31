package com.jdevtree.auth.backend.service.impl;

import com.jdevtree.auth.backend.dto.UserDto;
import com.jdevtree.auth.backend.enums.AuthProviderEnum;
import com.jdevtree.auth.backend.service.JwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
@Slf4j
public class JwtTokenServiceImpl implements JwtTokenService {
    private final SecretKey secretKey;
    private final long expiration;

    public JwtTokenServiceImpl(@Value("${jwt.secret}") String secret,
                               @Value("${jwt.expiration}") long expiration) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expiration = expiration;
    }


    @Override
    public String generateToken(UserDto user) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(user.getId())
                .claim("email", user.getEmail())
                .claim("provider", user.getProvider().name())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(expiration)))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            log.warn("Invalid JWT: {}", ex.getMessage());
            return false;
        }
    }

    @Override
    public UserDto extractUser(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return UserDto.builder()
                .id(claims.getSubject())
                .email(claims.get("email", String.class))
                .provider(AuthProviderEnum.valueOf(claims.get("provider", String.class)))
                .build();
    }

    @Override
    public long getExpirationTime() {
        return expiration;
    }
}
