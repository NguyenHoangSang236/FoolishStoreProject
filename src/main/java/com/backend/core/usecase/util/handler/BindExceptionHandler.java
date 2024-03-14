package com.backend.core.usecase.util.handler;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BindExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // Nếu validate fail thì trả về 400
    public static String getHandleBindException(BindException e) {
        // return value of first error
//        String errorMessage = "Request không hợp lệ";
//        if (e.getBindingResult().hasErrors())
//            e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
//        return errorMessage;
        if (e.getBindingResult().getAllErrors().size() > 1) {
            return "Please fill in all fields of information";
        } else return e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    }
}
