package com.jdevtree.auth.backend.api.common;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserView {
    private UUID id;
    private String email;
    private String name;
    private String avatar;
}
