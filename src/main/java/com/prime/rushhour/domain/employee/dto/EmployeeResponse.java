package com.prime.rushhour.domain.employee.dto;

import com.prime.rushhour.domain.account.dto.AccountResponse;

import java.time.LocalDate;

public record EmployeeResponse(
        String title,
        String phone,
        Double ratePerHour,
        LocalDate hireDate,
        AccountResponse accountResponse
) {
}
