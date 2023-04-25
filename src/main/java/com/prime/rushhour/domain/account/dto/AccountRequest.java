package com.prime.rushhour.domain.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AccountRequest(
        @Email(message = "Please provide a valid email")
        String email,
        @Pattern(regexp = "^[a-zA-Z'-]")
        @Size(min = 3, message = "Name must be at least 3 characters long")
        String fullName,
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
        String password,
        String roleName
) {
}
