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

import com.prime.rushhour.domain.account.dto.AccountRequest;

public class EmployeeRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidEmployeeRequest() {
        AccountRequest accountRequest = new AccountRequest("test@example.com", "John Doe", "Password123+", 1L);
        EmployeeRequest employeeRequest = new EmployeeRequest(
                "Manager",
                "+1234567890",
                50.0,
                LocalDate.now(),
                accountRequest,
                1L
        );

        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(employeeRequest);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidPhone() {
        AccountRequest accountRequest = new AccountRequest("test@example.com", "John Doe", "Password123+", 1L);
        EmployeeRequest employeeRequest = new EmployeeRequest(
                "Manager",
                "123-abc",
                50.0,
                LocalDate.now(),
                accountRequest,
                1L
        );

        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(employeeRequest);

        assertEquals(1, violations.size());
        assertEquals("Phone number must only be numbers", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidTitle() {
        AccountRequest accountRequest = new AccountRequest("test@example.com", "John Doe", "Password123+", 1L);
        EmployeeRequest employeeRequest = new EmployeeRequest(
                "M",
                "+1234567890",
                50.0,
                LocalDate.now(),
                accountRequest,
                1L
        );

        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(employeeRequest);

        assertEquals(1, violations.size());
        assertEquals("Title must be at least 2 characters long", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidRatePerHour() {
        AccountRequest accountRequest = new AccountRequest("test@example.com", "John Doe", "Password123+", 1L);
        EmployeeRequest employeeRequest = new EmployeeRequest(
                "Manager",
                "+1234567890",
                -10.0,
                LocalDate.now(),
                accountRequest,
                1L
        );

        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(employeeRequest);

        assertEquals(1, violations.size());
        assertEquals("Rate Per Hour must be a positive number", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidAccount() {
        AccountRequest accountRequest = new AccountRequest("invalid-email", "Jo", "pass", 1L);
        EmployeeRequest employeeRequest = new EmployeeRequest(
                "Manager",
                "+1234567890",
                50.0,
                LocalDate.now(),
                accountRequest,
                1L
        );

        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(employeeRequest);

        assertEquals(3, violations.size());
    }
}
