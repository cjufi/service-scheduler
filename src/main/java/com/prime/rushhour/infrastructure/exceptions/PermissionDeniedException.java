package com.prime.rushhour.infrastructure.exceptions;

public class PermissionDeniedException extends RuntimeException{

    public PermissionDeniedException(String role) {
        super("You don't have permission to assign the role of type: " + role);
    }
}
