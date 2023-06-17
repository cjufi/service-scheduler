package com.prime.rushhour.domain.appointment.dto;

import com.prime.rushhour.domain.activity.dto.ActivityResponse;
import com.prime.rushhour.domain.client.dto.ClientResponse;
import com.prime.rushhour.domain.employee.dto.EmployeeResponse;

import java.time.LocalDateTime;

public record AppointmentResponse(
        LocalDateTime startDate,
        LocalDateTime endDate,
        EmployeeResponse employee,
        ClientResponse client,
        ActivityResponse activity
) {
}
