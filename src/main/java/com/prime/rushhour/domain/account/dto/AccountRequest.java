package com.prime.rushhour.domain.account.dto;

public record AccountRequest(
        String email,
        String fullName,
        String password,
        String roleName
) {
}
