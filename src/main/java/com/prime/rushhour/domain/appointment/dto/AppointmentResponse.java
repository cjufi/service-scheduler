package com.prime.rushhour.domain.appointment.dto;

import com.prime.rushhour.domain.activity.dto.ActivityResponse;
import com.prime.rushhour.domain.client.dto.ClientResponse;
import com.prime.rushhour.domain.employee.dto.EmployeeResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record AppointmentResponse(
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal price,
        EmployeeResponse employee,
        ClientResponse client,
        List<ActivityResponse> activities
) {
}
