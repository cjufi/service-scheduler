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

import com.prime.rushhour.domain.account.dto.AccountRequest;

public class ClientRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidClientRequest() {
        AccountRequest accountRequest = new AccountRequest("test@example.com", "John Doe", "Password123+", 1L);
        ClientRequest clientRequest = new ClientRequest("+1234567890", "123 Main St", accountRequest);

        Set<ConstraintViolation<ClientRequest>> violations = validator.validate(clientRequest);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<ClientRequest> violation : violations) {
                System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
            }
        }

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidPhone() {
        AccountRequest accountRequest = new AccountRequest("test@example.com", "John Doe", "Password123+", 1L);
        ClientRequest clientRequest = new ClientRequest("123-abc", "123 Main St", accountRequest);

        Set<ConstraintViolation<ClientRequest>> violations = validator.validate(clientRequest);

        assertEquals(1, violations.size());
        assertEquals("Phone number must contain numbers only and can optionally start with +", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidAddress() {
        AccountRequest accountRequest = new AccountRequest("test@example.com", "John Doe", "Password123+", 1L);
        ClientRequest clientRequest = new ClientRequest("+1234567890", "12", accountRequest);

        Set<ConstraintViolation<ClientRequest>> violations = validator.validate(clientRequest);

        assertEquals(1, violations.size());
        assertEquals("Address must be at least 3 characters long", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidAccount() {
        AccountRequest accountRequest = new AccountRequest("invalid-email", "Jo", "pass", 1L);
        ClientRequest clientRequest = new ClientRequest("+1234567890", "123 Main St", accountRequest);

        Set<ConstraintViolation<ClientRequest>> violations = validator.validate(clientRequest);

        assertEquals(3, violations.size());
    }
}