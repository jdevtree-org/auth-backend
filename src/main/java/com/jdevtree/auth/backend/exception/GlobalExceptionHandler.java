package com.jdevtree.auth.backend.exception;

import com.jdevtree.auth.backend.vo.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OAuthAuthenticationException.class)
    public ResponseBean oauthAuthenticationExceptionHandler(OAuthAuthenticationException ex) {
        return new ResponseBean(HttpStatus.UNAUTHORIZED, ex.code, ex.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseBean exceptionHandler(Exception ex) {
        return new ResponseBean(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage(), null);
    }

}
