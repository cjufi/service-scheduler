package com.prime.rushhour.domain.account.dto;

public record LoginRequest(
        String username,
        String password
) {
}
