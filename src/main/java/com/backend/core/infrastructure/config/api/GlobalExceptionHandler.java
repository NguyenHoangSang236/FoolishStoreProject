package com.backend.core.infrastructure.config.api;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {AuthenticationException.class})
    ResponseEntity<ApiResponse> handleAuthenticationException(AuthenticationException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(new ApiResponse("failed", ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    ResponseEntity<ApiResponse> handleBadCredentialsException(BadCredentialsException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(new ApiResponse("failed", ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {Exception.class})
    ResponseEntity<ApiResponse> handleException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
