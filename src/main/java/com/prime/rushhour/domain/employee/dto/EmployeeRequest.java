package com.prime.rushhour.domain.employee.dto;

import com.prime.rushhour.domain.account.dto.AccountRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record EmployeeRequest(
        @NotBlank(message = "Title is a mandatory field")
        @Size(min = 2, message = "Title must be at least 2 characters long")
        @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "Title can only be letters and numbers")
        String title,
        @NotBlank(message = "Phone is a mandatory field")
        @Pattern(regexp = "^\\+?\\d+$", message = "Phone number must only be numbers")
        String phone,
        @NotNull(message = "Rate Per Hour is a mandatory field")
        @Positive(message = "Rate Per Hour must be a positive number")
        Double ratePerHour,
        @NotNull(message = "Hire Date is a mandatory field")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate hireDate,
        @Valid
        AccountRequest account,

        Long providerId
) {
}
