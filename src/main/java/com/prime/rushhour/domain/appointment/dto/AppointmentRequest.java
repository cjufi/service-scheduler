package com.prime.rushhour.domain.appointment.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record AppointmentRequest(
        @NotNull(message = "Start Date is a mandatory field")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime startDate,
        Long employeeId,
        Long clientId,
        Long activityId
) {
}
