package com.example.ecommerce.exception;

import lombok.Getter;

public enum ErrorType {
    NOT_FOUND("not_found");

    @Getter
    private final String displayName;

    ErrorType(String displayName) {
        this.displayName = displayName;
    }
}
