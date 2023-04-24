package com.prime.rushhour.domain.role.dto;

import com.prime.rushhour.domain.role.entity.RoleType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RoleDto(
        @NotNull(message = "Role name is a mandatory field")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Name should be alphanumeric with underscores only")
        RoleType name) {
}