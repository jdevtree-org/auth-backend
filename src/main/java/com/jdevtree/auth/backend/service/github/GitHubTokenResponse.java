package com.jdevtree.auth.backend.service.github;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GitHubTokenResponse {

    @JsonProperty
    private String accessToken;

    @JsonProperty
    private String tokenType;

    private String scope;
}
