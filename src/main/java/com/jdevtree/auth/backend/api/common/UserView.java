package com.jdevtree.auth.backend.api.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserView {
    private String id;
    private String email;
    private String name;
    private String avatar;

}
