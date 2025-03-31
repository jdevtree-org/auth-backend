package com.jdevtree.auth.backend.exception;

import com.jdevtree.auth.backend.enums.AuthResponseCodeEnum;

public class OAuthAuthenticationException extends AuthBackendException {
    public OAuthAuthenticationException(String message) {
        super(AuthResponseCodeEnum.GITHUB_UNAUTHORIZED, message);
    }
}
