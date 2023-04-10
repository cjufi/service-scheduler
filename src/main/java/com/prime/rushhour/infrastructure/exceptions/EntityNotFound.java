package com.prime.rushhour.infrastructure.exceptions;

public class EntityNotFound extends RuntimeException{

    public EntityNotFound(String entity, String field, Long value){
        super(entity + " with" + field + " " + value + " doesn't exist");
    }
}