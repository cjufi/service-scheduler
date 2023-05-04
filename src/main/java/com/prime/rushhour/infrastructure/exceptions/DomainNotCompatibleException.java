package com.prime.rushhour.infrastructure.exceptions;

public class DomainNotCompatibleException extends RuntimeException{

    private final String fieldName;
    public DomainNotCompatibleException(String fieldName, String fieldValue) {
        super(fieldValue + " is not compatible with the provider");
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
