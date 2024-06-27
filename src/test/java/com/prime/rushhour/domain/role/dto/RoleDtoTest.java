package com.prime.rushhour.domain.role.dto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RoleDtoTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidRoleDto() {
        RoleDto roleDto = new RoleDto("Admin_Role");

        Set<ConstraintViolation<RoleDto>> violations = validator.validate(roleDto);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<RoleDto> violation : violations) {
                System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
            }
        }

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidRoleNameShort() {
        RoleDto roleDto = new RoleDto("Ad");

        Set<ConstraintViolation<RoleDto>> violations = validator.validate(roleDto);

        assertEquals(1, violations.size());
        assertEquals("Name must be at least 3 characters long", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidRoleNamePattern() {
        RoleDto roleDto = new RoleDto("Admin-Role");

        Set<ConstraintViolation<RoleDto>> violations = validator.validate(roleDto);

        assertEquals(1, violations.size());
        assertEquals("Name should be alphanumeric with underscores only", violations.iterator().next().getMessage());
    }
}
