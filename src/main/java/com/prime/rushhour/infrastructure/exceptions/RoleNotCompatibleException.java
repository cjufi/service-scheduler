package com.prime.rushhour.infrastructure.exceptions;

public class RoleNotCompatibleException extends RuntimeException{

    public RoleNotCompatibleException(String entity,Long id) {
        super(entity + " can't be a type of role id: " + id);
    }
}
