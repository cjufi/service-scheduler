package com.prime.rushhour.domain.activity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record ActivityRequest(

        @NotBlank(message = "Name is a mandatory field")
        @Size(min = 2, message = "Name must be at least 2 characters long ")
        String name,
        @NotNull(message = "Price is a mandatory field")
        @Min(value = 0, message = "Price must be greater than 0")
        BigDecimal price,
        @NotNull(message = "Duration is a mandatory field")
        @Min(value = 0, message = "Duration must be greater than 0")
        Long duration,
        Long providerId,
        List<Long> employeeIds
) {
}
