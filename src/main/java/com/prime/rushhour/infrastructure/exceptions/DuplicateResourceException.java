package com.prime.rushhour.infrastructure.exceptions;

public class DuplicateResourceException extends RuntimeException{
    private final String fieldName;
    public DuplicateResourceException(String fieldName, String fieldValue) {
        super(fieldValue + " already exists");
        this.fieldName = fieldName;
    }
    public String getFieldName() {
        return fieldName;
    }
}