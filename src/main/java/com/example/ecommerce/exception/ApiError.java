package com.example.ecommerce.exception;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public record ApiError(OffsetDateTime timestamp, Integer status, ErrorType errorType, String message) {

    public static ApiError create(int httpStatus, ErrorType errorType, String message) {
        return new ApiError(OffsetDateTime.now(ZoneOffset.UTC), httpStatus, errorType, message);
    }
}
