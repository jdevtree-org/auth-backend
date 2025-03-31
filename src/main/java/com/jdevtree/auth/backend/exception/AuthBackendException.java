package com.jdevtree.auth.backend.exception;


import com.jdevtree.auth.backend.enums.AuthResponseCodeEnum;

public class AuthBackendException extends RuntimeException {
    String code;
    Object extra;
    Object data;

    AuthBackendException(AuthResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum.getMessage());
        this.code = responseCodeEnum.getCode();
    }

    AuthBackendException(AuthResponseCodeEnum responseCodeEnum, String message) {
        super(message);
        this.code = responseCodeEnum.getCode();
    }
}
