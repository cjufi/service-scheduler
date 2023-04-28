package com.prime.rushhour.domain.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AccountRequest(
        @NotBlank(message = "Email is a mandatory field")
        @Email(message = "Please provide a valid email")
        String email,
        @NotBlank(message = "Name is a mandatory field")
        @Pattern(regexp = "^[a-zA-Z'-]+")
        @Size(min = 3, message = "Name must be at least 3 characters long")
        String fullName,
        @NotBlank(message = "Password is a mandatory field")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
        String password,
        Long roleId
) {
}
