package com.jdevtree.auth.backend.service.github.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GitHubTokenResponse {

    @JsonProperty
    private String access_token;

    @JsonProperty
    private String token_type;

    private String scope;
}
