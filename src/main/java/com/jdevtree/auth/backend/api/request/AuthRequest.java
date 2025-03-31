package com.jdevtree.auth.backend.api.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String code;
    private String redirectUrl;
}
