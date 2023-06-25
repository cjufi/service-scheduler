package com.prime.rushhour.infrastructure.exceptions;

public class ForbiddenActivityException extends RuntimeException{

    public ForbiddenActivityException(Long id) {
        super("You cannot access the activity with the id: " + id);
    }
}
