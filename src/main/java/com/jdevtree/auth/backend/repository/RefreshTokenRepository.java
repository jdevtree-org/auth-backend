package com.jdevtree.auth.backend.repository;

import com.jdevtree.auth.backend.entity.RefreshToken;
import com.jdevtree.auth.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}
