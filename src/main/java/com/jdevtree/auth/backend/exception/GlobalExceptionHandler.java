package com.jdevtree.auth.backend.exception;

import com.jdevtree.auth.backend.enums.AuthResponseCodeEnum;
import com.jdevtree.auth.backend.vo.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OAuthAuthenticationException.class)
    public ResponseBean oauthAuthenticationExceptionHandler(OAuthAuthenticationException ex) {
        return new ResponseBean(HttpStatus.UNAUTHORIZED, Map.of(
                "error", AuthResponseCodeEnum.GITHUB_UNAUTHORIZED,
                "message", ex.getMessage()
        ));
    }

}
