package com.prime.rushhour.domain.role.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
    }

    @AfterEach
    void tearDown() {
        role = null;
    }

    @Test
    void testSetName() {
        String name = "Admin";
        role.setName(name);
        assertEquals(name, role.getName());
    }

    @Test
    void testSetId() {
        Long id = 1L;
        role.setId(id);
        assertEquals(id, role.getId());
    }

    @Test
    void testConstructorWithName() {
        String name = "User";
        Role roleWithName = new Role(name);
        assertEquals(name, roleWithName.getName());
    }

    @Test
    void testToString() {
        Long id = 1L;
        String name = "Admin";
        role.setId(id);
        role.setName(name);

        String roleString = role.toString();

        assertTrue(roleString.contains(id.toString()));
        assertTrue(roleString.contains(name));
    }

    @Test
    void testEqualsSameRole() {
        assertTrue(role.equals(role));
    }

    @Test
    void testEqualsNull() {
        assertFalse(role.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(role.equals(new String("test")));
    }

    @ParameterizedTest
    @CsvSource({
            "1, Admin, 1, Admin, true",
            "1, Admin, 2, User, false",
            "1, Admin, 1, User, false",
            "1, Admin, 2, Admin, false"
    })
    void testEqualsAllFields(Long id1, String name1, Long id2, String name2, boolean expected) {
        role.setId(id1);
        role.setName(name1);

        Role anotherRole = new Role();
        anotherRole.setId(id2);
        anotherRole.setName(name2);

        assertEquals(expected, role.equals(anotherRole));
    }
}
