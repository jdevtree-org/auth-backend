package com.jdevtree.auth.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResultDto {
    private String accessToken;
    private Integer expiresIn;
    private String refreshToken;
    private UserDto user;
}
