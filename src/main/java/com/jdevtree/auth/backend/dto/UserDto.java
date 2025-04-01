package com.jdevtree.auth.backend.dto;

import com.jdevtree.auth.backend.entity.User;
import com.jdevtree.auth.backend.enums.AuthProviderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private UUID id;
    private String email;
    private String name;
    private String avatar;
    private AuthProviderEnum provider;

    public static UserDto from(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getName(), user.getAvatar(), AuthProviderEnum.GITHUB);
    }
}
