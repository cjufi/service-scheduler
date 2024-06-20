package com.prime.rushhour.domain.client.entity;

import com.prime.rushhour.domain.account.entity.Account;
import com.prime.rushhour.domain.appointment.entity.Appointment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    Client client;
    Account account;
    Set<Appointment> appointments;

    @BeforeEach
    void setUp() {
        account = new Account();
        appointments = new HashSet<>();
        client = new Client();
    }

    @AfterEach
    void tearDown() {
        client = null;
    }

    @Test
    void testSetPhone() {
        String phone = "123-456-7890";
        client.setPhone(phone);
        assertEquals(phone, client.getPhone());
    }

    @Test
    void testSetAddress() {
        String address = "123 Main St";
        client.setAddress(address);
        assertEquals(address, client.getAddress());
    }

    @Test
    void testSetAccount() {
        client.setAccount(account);
        assertEquals(account, client.getAccount());
    }

    @Test
    void testSetAppointments() {
        Appointment appointment1 = new Appointment();
        Appointment appointment2 = new Appointment();
        appointments.add(appointment1);
        appointments.add(appointment2);
        client.setAppointments(appointments);
        assertEquals(appointments, client.getAppointments());
    }

    @Test
    void testToString() {
        String phone = "123-456-7890";
        String address = "123 Main St";
        client.setPhone(phone);
        client.setAddress(address);
        client.setAccount(account);
        client.setAppointments(appointments);

        String clientString = client.toString();

        assertTrue(clientString.contains(phone));
        assertTrue(clientString.contains(address));
        assertTrue(clientString.contains(account.toString()));
        assertTrue(clientString.contains(appointments.toString()));
    }

    @Test
    void testEqualsSameAddress() {
        assertTrue(client.equals(client));
    }

    @Test
    void testEqualsNull() {
        assertFalse(client.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(client.equals(new String("test")));
    }

    @ParameterizedTest
    @CsvSource({
            "123-456-7890, 123 Main St, 123-456-7890, 123 Main St, true",
            "123-456-7890, 123 Main St, 123-456-7890, 456 Elm St, false",
            "123-456-7890, 123 Main St, 987-654-3210, 123 Main St, false",
            "123-456-7890, 123 Main St, 987-654-3210, 456 Elm St, false"
    })
    void testEqualsAllFields(String phone1, String address1, String phone2, String address2, boolean expected) {
        client.setPhone(phone1);
        client.setAddress(address1);
        client.setAccount(account);
        client.setAppointments(appointments);

        Client anotherClient = new Client();
        anotherClient.setPhone(phone2);
        anotherClient.setAddress(address2);
        anotherClient.setAccount(account);
        anotherClient.setAppointments(appointments);

        assertEquals(expected, client.equals(anotherClient));
    }
}
