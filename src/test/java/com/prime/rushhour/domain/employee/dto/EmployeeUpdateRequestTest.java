package com.prime.rushhour.domain.employee.dto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.prime.rushhour.domain.account.dto.AccountUpdateRequest;

public class EmployeeUpdateRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidEmployeeUpdateRequest() {
        AccountUpdateRequest accountUpdateRequest = new AccountUpdateRequest("John Doe", "Password123+", 1L);
        EmployeeUpdateRequest employeeUpdateRequest = new EmployeeUpdateRequest(
                "Manager",
                "+1234567890",
                50.0,
                LocalDate.now(),
                accountUpdateRequest,
                1L
        );

        Set<ConstraintViolation<EmployeeUpdateRequest>> violations = validator.validate(employeeUpdateRequest);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidPhone() {
        AccountUpdateRequest accountUpdateRequest = new AccountUpdateRequest("John Doe", "Password123+", 1L);
        EmployeeUpdateRequest employeeUpdateRequest = new EmployeeUpdateRequest(
                "Manager",
                "123-abc",
                50.0,
                LocalDate.now(),
                accountUpdateRequest,
                1L
        );

        Set<ConstraintViolation<EmployeeUpdateRequest>> violations = validator.validate(employeeUpdateRequest);

        assertEquals(1, violations.size());
        assertEquals("must match \"^\\+?\\d+$\"", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidTitle() {
        AccountUpdateRequest accountUpdateRequest = new AccountUpdateRequest("John Doe", "Password123+", 1L);
        EmployeeUpdateRequest employeeUpdateRequest = new EmployeeUpdateRequest(
                "M",
                "+1234567890",
                50.0,
                LocalDate.now(),
                accountUpdateRequest,
                1L
        );

        Set<ConstraintViolation<EmployeeUpdateRequest>> violations = validator.validate(employeeUpdateRequest);

        assertEquals(1, violations.size());
        assertEquals("Title must be at least 2 characters long", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidRatePerHour() {
        AccountUpdateRequest accountUpdateRequest = new AccountUpdateRequest("John Doe", "Password123+", 1L);
        EmployeeUpdateRequest employeeUpdateRequest = new EmployeeUpdateRequest(
                "Manager",
                "+1234567890",
                -10.0,
                LocalDate.now(),
                accountUpdateRequest,
                1L
        );

        Set<ConstraintViolation<EmployeeUpdateRequest>> violations = validator.validate(employeeUpdateRequest);

        assertEquals(1, violations.size());
        assertEquals("Rate Per Hour must be a positive number", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidAccount() {
        AccountUpdateRequest accountUpdateRequest = new AccountUpdateRequest("Jo", "pass", 1L);
        EmployeeUpdateRequest employeeUpdateRequest = new EmployeeUpdateRequest(
                "Manager",
                "+1234567890",
                50.0,
                LocalDate.now(),
                accountUpdateRequest,
                1L
        );

        Set<ConstraintViolation<EmployeeUpdateRequest>> violations = validator.validate(employeeUpdateRequest);

        assertEquals(2, violations.size());
    }
}
