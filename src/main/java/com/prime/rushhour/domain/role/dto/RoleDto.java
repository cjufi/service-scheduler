package com.prime.rushhour.domain.role.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RoleDto(
        @NotBlank(message = "Role name is a mandatory field")
        @Size(min = 3, message = "Name must be at least 3 characters long")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Name should be alphanumeric with underscores only")
        String name) {
}