package com.prime.rushhour.domain.account.dto;

public record AccountResponse(
        String email,
        String fullName,
        String roleName
) {
}
