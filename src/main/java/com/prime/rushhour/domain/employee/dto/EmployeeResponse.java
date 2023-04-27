package com.prime.rushhour.domain.employee.dto;

import java.time.LocalDate;

public record EmployeeResponse(
        String title,
        String phone,
        Double ratePerHour,
        LocalDate hireDate
) {
}
