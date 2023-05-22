package com.prime.rushhour.infrastructure.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entity, String field, Object value) {
        super(entity + " with " + field + " " + value + " doesn't exist");
    }
}