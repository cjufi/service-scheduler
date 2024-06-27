package com.prime.rushhour.domain.activity.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ActivityRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidActivityRequest() {
        ActivityRequest activityRequest = new ActivityRequest("Yoga Class", BigDecimal.valueOf(50), 60L, 1L, List.of(1L, 2L));

        Set<ConstraintViolation<ActivityRequest>> violations = validator.validate(activityRequest);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<ActivityRequest> violation : violations) {
                System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
            }
        }

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidName() {
        ActivityRequest activityRequest = new ActivityRequest("A", BigDecimal.valueOf(50), 60L, 1L, List.of(1L, 2L));

        Set<ConstraintViolation<ActivityRequest>> violations = validator.validate(activityRequest);

        assertEquals(1, violations.size());
        assertEquals("Name must be at least 2 characters long ", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidPrice() {
        ActivityRequest activityRequest = new ActivityRequest("Yoga Class", BigDecimal.valueOf(-10), 60L, 1L, List.of(1L, 2L));

        Set<ConstraintViolation<ActivityRequest>> violations = validator.validate(activityRequest);

        assertEquals(1, violations.size());
        assertEquals("Price must be greater than 0", violations.iterator().next().getMessage());
    }

    @Test
    public void testNullPrice() {
        ActivityRequest activityRequest = new ActivityRequest("Yoga Class", null, 60L, 1L, List.of(1L, 2L));

        Set<ConstraintViolation<ActivityRequest>> violations = validator.validate(activityRequest);

        assertEquals(1, violations.size());
        assertEquals("Price is a mandatory field", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidDuration() {
        ActivityRequest activityRequest = new ActivityRequest("Yoga Class", BigDecimal.valueOf(50), -10L, 1L, List.of(1L, 2L));

        Set<ConstraintViolation<ActivityRequest>> violations = validator.validate(activityRequest);

        assertEquals(1, violations.size());
        assertEquals("Duration must be greater than 0", violations.iterator().next().getMessage());
    }

    @Test
    public void testNullDuration() {
        ActivityRequest activityRequest = new ActivityRequest("Yoga Class", BigDecimal.valueOf(50), null, 1L, List.of(1L, 2L));

        Set<ConstraintViolation<ActivityRequest>> violations = validator.validate(activityRequest);

        assertEquals(1, violations.size());
        assertEquals("Duration is a mandatory field", violations.iterator().next().getMessage());
    }
}
