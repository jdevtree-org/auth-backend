package com.jdevtree.auth.backend.enums;

public enum AuthResponseCodeEnum {
    SUCCESS("SUCCESS"),
    GITHUB_UNAUTHORIZED("Your GitHub request was Unauthorized");

    private final String message;

    AuthResponseCodeEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return name();
    }
}
