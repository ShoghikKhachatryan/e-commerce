package com.example.ecommerce.exception;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<ApiError> handleException(ClientErrorException e) {
        System.out.println("I am cootched " + e.getMessage() + " " + e.getStatus());
        var res = ResponseEntity.status(e.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(ApiError.create(e.getStatus(), e.getErrorType(), e.getMessage()));
        System.out.println("I am cootched " + res);
        return res;
    }
}
