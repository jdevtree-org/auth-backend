package com.jdevtree.auth.backend.controller;

import com.jdevtree.auth.backend.api.request.AuthRequest;
import com.jdevtree.auth.backend.vo.ResponseBean;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/health")
    public String health() {
        return "Endpoint is healthy";
    }

    @PostMapping("/oauth/github")
    public ResponseBean githubLogin(@Valid @RequestBody AuthRequest request) {

    }
}
