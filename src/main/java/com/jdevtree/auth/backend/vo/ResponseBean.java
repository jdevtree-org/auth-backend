package com.jdevtree.auth.backend.vo;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class ResponseBean extends ResponseEntity<Object> {

    public ResponseBean(HttpStatusCode status, String code, String message, Object data, Object extra) {
        super(Map.of(
                "code", code,
                "message", message,
                "data", data,
                "extra", extra
        ),status);
    }

    public ResponseBean(HttpStatusCode status, String code, String message, Object data) {
        this(status, code, message, data, null);
    }

    public ResponseBean(String code, String message, Object data) {
        this(HttpStatus.OK, code, message, data, null);
    }

    public ResponseBean(String code, String message, Object data, Object extra) {
        this(HttpStatus.OK, code, message, data, extra);
    }

    public ResponseBean(String code, String message) {
        this(HttpStatus.OK, code, message, null);
    }

    public ResponseBean(HttpStatusCode status, Map<?, ?> extra) {
        this(
                status,
                (String) extra.get("error"),
                (String) extra.get("message"),
                null,
                extra
        );
    }
}
