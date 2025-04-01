package com.jdevtree.auth.backend.exception;

import com.jdevtree.auth.backend.enums.AuthResponseCodeEnum;

public class InvalidRefreshTokenException extends AuthBackendException {
    public InvalidRefreshTokenException(String message) {
        super(AuthResponseCodeEnum.INVALID_REFRESH_TOKEN, message);
    }
}
