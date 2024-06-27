package com.prime.rushhour.domain.account.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AccountUpdateRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidAccountUpdateRequest() {
        AccountUpdateRequest accountUpdateRequest = new AccountUpdateRequest("John Doe", "Password123+", 1L);

        Set<ConstraintViolation<AccountUpdateRequest>> violations = validator.validate(accountUpdateRequest);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<AccountUpdateRequest> violation : violations) {
                System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
            }
        }

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidFullName() {
        AccountUpdateRequest accountUpdateRequest = new AccountUpdateRequest("Jo", "Password123+", 1L);

        Set<ConstraintViolation<AccountUpdateRequest>> violations = validator.validate(accountUpdateRequest);

        assertEquals(1, violations.size());
        assertEquals("Name must be at least 3 characters long", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidPassword() {
        AccountUpdateRequest accountUpdateRequest = new AccountUpdateRequest("John Doe", "pass", 1L);

        Set<ConstraintViolation<AccountUpdateRequest>> violations = validator.validate(accountUpdateRequest);

        assertEquals(1, violations.size());
        assertEquals("Password should contain at least 8 characters, one uppercase, one lowercase, one digit, and a special symbol", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidFullNameCharacters() {
        AccountUpdateRequest accountUpdateRequest = new AccountUpdateRequest("John123", "Password123+", 1L);

        Set<ConstraintViolation<AccountUpdateRequest>> violations = validator.validate(accountUpdateRequest);

        assertEquals(1, violations.size());
        assertEquals("Only letters, hyphens and apostrophes are allowed", violations.iterator().next().getMessage());
    }

}