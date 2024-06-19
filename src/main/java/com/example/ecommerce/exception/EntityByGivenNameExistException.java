package com.example.ecommerce.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class EntityByGivenNameExistException extends ClientErrorException {

    public EntityByGivenNameExistException(String name) {
        super(String.format("Entity with name '%s' already exist.", name), HttpStatus.CONFLICT, ErrorType.ALREADY_ENTITY_EXIST);
    }
}
