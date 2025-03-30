package com.jdevtree.auth.backend.enums;

public enum AuthResponseEnum {
    SUCCESS("SUCCESS");

    private final String message;

    AuthResponseEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    String getCode() {
        return name();
    }
}
