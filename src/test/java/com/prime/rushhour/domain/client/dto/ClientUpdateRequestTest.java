package com.prime.rushhour.domain.client.dto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.prime.rushhour.domain.account.dto.AccountUpdateRequest;

public class ClientUpdateRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidClientUpdateRequest() {
        AccountUpdateRequest accountUpdateRequest = new AccountUpdateRequest("John Doe", "Password123+", 1L);
        ClientUpdateRequest clientUpdateRequest = new ClientUpdateRequest("+1234567890", "123 Main St", accountUpdateRequest);

        Set<ConstraintViolation<ClientUpdateRequest>> violations = validator.validate(clientUpdateRequest);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<ClientUpdateRequest> violation : violations) {
                System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
            }
        }

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidPhone() {
        AccountUpdateRequest accountUpdateRequest = new AccountUpdateRequest("John Doe", "Password123+", 1L);
        ClientUpdateRequest clientUpdateRequest = new ClientUpdateRequest("123-abc", "123 Main St", accountUpdateRequest);

        Set<ConstraintViolation<ClientUpdateRequest>> violations = validator.validate(clientUpdateRequest);

        assertEquals(1, violations.size());
        assertEquals("Phone number must contain numbers only and can optionally start with +", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidAddress() {
        AccountUpdateRequest accountUpdateRequest = new AccountUpdateRequest("John Doe", "Password123+", 1L);
        ClientUpdateRequest clientUpdateRequest = new ClientUpdateRequest("+1234567890", "12", accountUpdateRequest);

        Set<ConstraintViolation<ClientUpdateRequest>> violations = validator.validate(clientUpdateRequest);

        assertEquals(1, violations.size());
        assertEquals("Address must be at least 3 characters long", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidAccount() {
        AccountUpdateRequest accountUpdateRequest = new AccountUpdateRequest("Jo", "pass", 1L);
        ClientUpdateRequest clientUpdateRequest = new ClientUpdateRequest("+1234567890", "123 Main St", accountUpdateRequest);

        Set<ConstraintViolation<ClientUpdateRequest>> violations = validator.validate(clientUpdateRequest);

        assertEquals(2, violations.size());
    }
}
