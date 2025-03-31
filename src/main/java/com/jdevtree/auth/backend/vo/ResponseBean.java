package com.jdevtree.auth.backend.vo;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseBean extends ResponseEntity<Object> {

    public ResponseBean(HttpStatusCode status, String code, String message, Object data, Object extra) {
        super(buildBody(code, message, data, extra), status);
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
                extra.get("error") != null ? extra.get("error").toString() : null,
                extra.get("message") != null ? extra.get("message").toString() : null,
                null,
                extra
        );
    }

    private static Map<String, Object> buildBody(String code, String message, Object data, Object extra) {
        Map<String, Object> map = new HashMap<>();
        if (code != null) map.put("code", code);
        if (message != null) map.put("message", message);
        if (data != null) map.put("data", data);
        if (extra != null) map.put("extra", extra);
        return map;
    }
}
