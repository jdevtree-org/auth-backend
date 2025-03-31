package com.jdevtree.auth.backend.dto;

import com.jdevtree.auth.backend.enums.AuthProviderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String id;
    private String email;
    private String name;
    private String avatar;
    private AuthProviderEnum provider;
}
