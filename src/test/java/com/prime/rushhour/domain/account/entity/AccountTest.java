package com.prime.rushhour.domain.account.entity;

import com.prime.rushhour.domain.role.entity.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    Account account;
    Role role;

    @BeforeEach
    void setUp() throws Exception {
        role = new Role();
        role.setName("USER");
        account = new Account();
    }

    @AfterEach
    void tearDown() throws Exception {
        account = null;
    }

    @Test
    void testSetEmail() {
        account.setEmail("test@example.com");
        assertEquals("test@example.com", account.getEmail());
    }

    @Test
    void testSetFullName() {
        account.setFullName("John Doe");
        assertEquals("John Doe", account.getFullName());
    }

    @Test
    void testSetPassword() {
        account.setPassword("securepassword123");
        assertEquals("securepassword123", account.getPassword());
    }

    @Test
    void testSetRole() {
        account.setRole(role);
        assertEquals(role, account.getRole());
    }

    @Test
    void testToString() {
        account.setEmail("john@example.com");
        account.setFullName("John Doe");
        account.setPassword("password");
        account.setRole(role);

        String accountString = account.toString();

        assertTrue(accountString.contains("john@example.com"));
        assertTrue(accountString.contains("John Doe"));
        assertTrue(accountString.contains("password"));
    }

    @Test
    void testEqualsSameAddress() {
        assertTrue(account.equals(account));
    }

    @Test
    void testEqualsNull() {
        assertFalse(account.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(account.equals(new String("test")));
    }

    @ParameterizedTest
    @CsvSource({
            "test@example.com, John Doe, test@example.com, John Doe, true",
            "test1@example.com, John Doe, test@example.com, John Doe, false",
            "test@example.com, Jane Doe, test@example.com, John Doe, false",
            "test1@example.com, Jane Doe, test@example.com, John Doe, false"
    })
    void testEqualsAllFields(String email1, String fullName1,
                             String email2, String fullName2, boolean expected) {
        account.setEmail(email1);
        account.setFullName(fullName1);
        account.setPassword("password");
        account.setRole(role);

        Account anotherAccount = new Account();
        anotherAccount.setEmail(email2);
        anotherAccount.setFullName(fullName2);
        anotherAccount.setPassword("password");
        anotherAccount.setRole(role);

        assertEquals(expected, account.equals(anotherAccount));
    }
}