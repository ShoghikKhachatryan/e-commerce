package com.example.ecommerce.exception;

import lombok.Getter;

public enum ErrorType {
    NOT_FOUND("not_found"),
    ALREADY_ENTITY_EXIST("already_entity_exist"); // TODO HK: ENTITY_ALREADY_EXIST

    @Getter
    private final String displayName;

    ErrorType(String displayName) {
        this.displayName = displayName;
    }
}
