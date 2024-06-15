package com.example.ecommerce.exception;

import lombok.AllArgsConstructor;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@AllArgsConstructor
public class ApiError {

    private final OffsetDateTime timestamp;

    private final Integer status;

    private final ErrorType errorType;

    private final String message;

    public static ApiError create(int httpStatus, ErrorType errorType, String message) {
        return new ApiError(OffsetDateTime.now(ZoneOffset.UTC), httpStatus, errorType, message);
    }
}
