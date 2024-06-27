package com.prime.rushhour.domain.appointment.dto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AppointmentRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidAppointmentRequest() {
        AppointmentRequest appointmentRequest = new AppointmentRequest(
                LocalDateTime.now(),
                1L,
                1L,
                List.of(1L, 2L)
        );

        Set<ConstraintViolation<AppointmentRequest>> violations = validator.validate(appointmentRequest);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testNullStartDate() {
        AppointmentRequest appointmentRequest = new AppointmentRequest(
                null,
                1L,
                1L,
                List.of(1L, 2L)
        );

        Set<ConstraintViolation<AppointmentRequest>> violations = validator.validate(appointmentRequest);

        assertEquals(1, violations.size());
        assertEquals("Start Date is a mandatory field", violations.iterator().next().getMessage());
    }
}
