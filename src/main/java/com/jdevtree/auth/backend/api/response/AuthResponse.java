package com.jdevtree.auth.backend.api.response;


import com.jdevtree.auth.backend.api.common.UserView;

public class AuthResponse {
    private String accessToken;
    private String tokenType;
    private Integer expiresIn;
    private UserView user;
    private String refreshToken;
}
