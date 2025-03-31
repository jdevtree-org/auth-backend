package com.jdevtree.auth.backend.service.github.model;

import lombok.Data;

@Data
public class GitHubEmailResponse {
    private String email;
    private boolean primary;
    private boolean verified;
    private String visibility;
}
