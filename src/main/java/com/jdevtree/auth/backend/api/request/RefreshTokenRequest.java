package com.jdevtree.auth.backend.api.request;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}
