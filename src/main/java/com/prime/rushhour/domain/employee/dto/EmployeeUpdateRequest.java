package com.prime.rushhour.domain.employee.dto;

import com.prime.rushhour.domain.account.dto.AccountUpdateRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record EmployeeUpdateRequest(
        @NotBlank(message = "Title is a mandatory field")
        @Size(min = 2, message = "Title must be at least 2 characters long")
        @Pattern(regexp = "^[a-zA-Z0-9 ]*$")
        String title,
        @NotBlank(message = "Phone is a mandatory field")
        @Pattern(regexp = "^\\+?\\d+$")
        String phone,
        @NotNull(message = "Rate Per Hour is a mandatory field")
        @Positive(message = "Rate Per Hour must be a positive number")
        Double ratePerHour,
        @NotNull(message = "Hire Date is a mandatory field")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate hireDate,
        @Valid
        AccountUpdateRequest account,

        Long providerId
) {
}
