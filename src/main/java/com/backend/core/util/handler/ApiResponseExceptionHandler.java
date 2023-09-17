package com.backend.core.util.handler;

import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.enums.ErrorTypeEnum;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApiResponseExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ApiResponse> handleForbiddenException(HttpClientErrorException.Forbidden ex) {
        return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleInternalServerException(Exception ex) {
        return new ResponseEntity<>(new ApiResponse("failed", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity<ApiResponse> handleBadRequestException(HttpClientErrorException.BadRequest ex) {
        return new ResponseEntity<>(new ApiResponse("failed", ex.getStatusText()), HttpStatus.BAD_REQUEST);
    }
}
