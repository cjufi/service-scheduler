package com.prime.rushhour.domain.account.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AccountRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidAccountRequest() {
        AccountRequest accountRequest = new AccountRequest("test@example.com", "John Doe", "Password123+", 1L);
        Set<ConstraintViolation<AccountRequest>> violations = validator.validate(accountRequest);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidEmail() {
        AccountRequest accountRequest = new AccountRequest("invalid-email", "John Doe", "Password123+", 1L);

        Set<ConstraintViolation<AccountRequest>> violations = validator.validate(accountRequest);

        assertEquals(1, violations.size());
        assertEquals("Please provide a valid email", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidName() {
        AccountRequest accountRequest = new AccountRequest("test@example.com", "Jo", "Password123+", 1L);

        Set<ConstraintViolation<AccountRequest>> violations = validator.validate(accountRequest);

        assertEquals(1, violations.size());
        assertEquals("Name must be at least 3 characters long", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidPassword() {
        AccountRequest accountRequest = new AccountRequest("test@example.com", "John Doe", "pass", 1L);

        Set<ConstraintViolation<AccountRequest>> violations = validator.validate(accountRequest);

        assertEquals(1, violations.size());
        assertEquals("Password should contain at least 8 characters, one uppercase, one lowercase, one digit, and a special symbol", violations.iterator().next().getMessage());
    }

}