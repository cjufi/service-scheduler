package com.prime.rushhour.domain.role.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RoleDto(
        @NotBlank(message = "Role name is a mandatory field")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Name should be alphanumeric with underscores only")
        String name) {
}