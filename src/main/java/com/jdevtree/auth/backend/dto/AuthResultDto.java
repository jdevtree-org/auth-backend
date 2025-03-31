package com.jdevtree.auth.backend.dto;

import lombok.Data;

@Data
public class AuthResultDto {
    private String accessToken;
    private Integer expiresIn;
    private String refreshToken;
    private UserDto user;
}
