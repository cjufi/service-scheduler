package com.prime.rushhour.domain.account.dto;

import com.prime.rushhour.domain.role.dto.RoleDto;

public record AccountResponse(
        String email,
        String fullName,
        RoleDto role
) {
}
