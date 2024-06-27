package com.prime.rushhour.domain.provider.dto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ProviderRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidProviderRequest() {
        Set<DayOfWeek> workingDays = new HashSet<>();
        workingDays.add(DayOfWeek.MONDAY);
        workingDays.add(DayOfWeek.TUESDAY);

        ProviderRequest providerRequest = new ProviderRequest(
                "Test Provider",
                "https://example.com",
                "example.domain",
                "+1234567890",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0),
                workingDays
        );

        Set<ConstraintViolation<ProviderRequest>> violations = validator.validate(providerRequest);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<ProviderRequest> violation : violations) {
                System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
            }
        }

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidName() {
        Set<DayOfWeek> workingDays = new HashSet<>();
        workingDays.add(DayOfWeek.MONDAY);
        workingDays.add(DayOfWeek.TUESDAY);

        ProviderRequest providerRequest = new ProviderRequest(
                "Te",
                "https://example.com",
                "example.domain",
                "+1234567890",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0),
                workingDays
        );

        Set<ConstraintViolation<ProviderRequest>> violations = validator.validate(providerRequest);

        assertEquals(1, violations.size());
        assertEquals("Name must be at least 3 characters long", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidWebsite() {
        Set<DayOfWeek> workingDays = new HashSet<>();
        workingDays.add(DayOfWeek.MONDAY);
        workingDays.add(DayOfWeek.TUESDAY);

        ProviderRequest providerRequest = new ProviderRequest(
                "Test Provider",
                "invalid-url",
                "example.domain",
                "+1234567890",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0),
                workingDays
        );

        Set<ConstraintViolation<ProviderRequest>> violations = validator.validate(providerRequest);

        assertEquals(1, violations.size());
        assertEquals("must be a valid URL", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidBusinessDomain() {
        Set<DayOfWeek> workingDays = new HashSet<>();
        workingDays.add(DayOfWeek.MONDAY);
        workingDays.add(DayOfWeek.TUESDAY);

        ProviderRequest providerRequest = new ProviderRequest(
                "Test Provider",
                "https://example.com",
                "invalid_domain",
                "+1234567890",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0),
                workingDays
        );

        Set<ConstraintViolation<ProviderRequest>> violations = validator.validate(providerRequest);

        assertEquals(1, violations.size());
        assertEquals("must match \"^[a-zA-Z\\d]+(\\.[a-zA-Z\\d]+)*$\"", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidPhone() {
        Set<DayOfWeek> workingDays = new HashSet<>();
        workingDays.add(DayOfWeek.MONDAY);
        workingDays.add(DayOfWeek.TUESDAY);

        ProviderRequest providerRequest = new ProviderRequest(
                "Test Provider",
                "https://example.com",
                "example.domain",
                "123-abc",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0),
                workingDays
        );

        Set<ConstraintViolation<ProviderRequest>> violations = validator.validate(providerRequest);

        assertEquals(1, violations.size());
        assertEquals("must match \"^\\+?\\d+$\"", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidStartTime() {
        Set<DayOfWeek> workingDays = new HashSet<>();
        workingDays.add(DayOfWeek.MONDAY);
        workingDays.add(DayOfWeek.TUESDAY);

        ProviderRequest providerRequest = new ProviderRequest(
                "Test Provider",
                "https://example.com",
                "example.domain",
                "+1234567890",
                null,
                LocalTime.of(17, 0),
                workingDays
        );

        Set<ConstraintViolation<ProviderRequest>> violations = validator.validate(providerRequest);

        assertEquals(1, violations.size());
        assertEquals("Start Time is a mandatory field", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidEndTime() {
        Set<DayOfWeek> workingDays = new HashSet<>();
        workingDays.add(DayOfWeek.MONDAY);
        workingDays.add(DayOfWeek.TUESDAY);

        ProviderRequest providerRequest = new ProviderRequest(
                "Test Provider",
                "https://example.com",
                "example.domain",
                "+1234567890",
                LocalTime.of(9, 0),
                null,
                workingDays
        );

        Set<ConstraintViolation<ProviderRequest>> violations = validator.validate(providerRequest);

        assertEquals(1, violations.size());
        assertEquals("End Time is a mandatory field", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidWorkingDays() {
        ProviderRequest providerRequest = new ProviderRequest(
                "Test Provider",
                "https://example.com",
                "example.domain",
                "+1234567890",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0),
                null
        );

        Set<ConstraintViolation<ProviderRequest>> violations = validator.validate(providerRequest);

        assertEquals(1, violations.size());
        assertEquals("Setting working days is a must", violations.iterator().next().getMessage());
    }
}
