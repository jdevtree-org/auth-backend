package com.jdevtree.auth.backend.repository;

import com.jdevtree.auth.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByGithubId(String githubId);

    Optional<User> findByEmail(String email);
}
