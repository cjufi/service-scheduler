package com.prime.rushhour.domain.appointment.entity;

import com.prime.rushhour.domain.activity.entity.Activity;
import com.prime.rushhour.domain.client.entity.Client;
import com.prime.rushhour.domain.employee.entity.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {

    Appointment appointment;
    Employee employee;
    Client client;
    List<Activity> activities;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        client = new Client();
        activities = new ArrayList<>();
        appointment = new Appointment();
    }

    @AfterEach
    void tearDown() {
        appointment = null;
    }

    @Test
    void testSetStartDate() {
        LocalDateTime startDate = LocalDateTime.of(2023, 6, 20, 9, 0);
        appointment.setStartDate(startDate);
        assertEquals(startDate, appointment.getStartDate());
    }

    @Test
    void testSetEndDate() {
        LocalDateTime endDate = LocalDateTime.of(2023, 6, 20, 10, 0);
        appointment.setEndDate(endDate);
        assertEquals(endDate, appointment.getEndDate());
    }

    @Test
    void testSetEmployee() {
        appointment.setEmployee(employee);
        assertEquals(employee, appointment.getEmployee());
    }

    @Test
    void testSetClient() {
        appointment.setClient(client);
        assertEquals(client, appointment.getClient());
    }

    @Test
    void testSetActivities() {
        Activity activity1 = new Activity();
        Activity activity2 = new Activity();
        activities.add(activity1);
        activities.add(activity2);
        appointment.setActivities(activities);
        assertEquals(activities, appointment.getActivities());
    }

    @Test
    void testToString() {
        LocalDateTime startDate = LocalDateTime.of(2023, 6, 20, 9, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 6, 20, 10, 0);
        appointment.setStartDate(startDate);
        appointment.setEndDate(endDate);
        appointment.setEmployee(employee);
        appointment.setClient(client);
        appointment.setActivities(activities);

        String appointmentString = appointment.toString();

        assertTrue(appointmentString.contains(startDate.toString()));
        assertTrue(appointmentString.contains(endDate.toString()));
        assertTrue(appointmentString.contains(employee.toString()));
        assertTrue(appointmentString.contains(client.toString()));
        assertTrue(appointmentString.contains(activities.toString()));
    }

    @Test
    void testEqualsSameAddress() {
        assertTrue(appointment.equals(appointment));
    }

    @Test
    void testEqualsNull() {
        assertFalse(appointment.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(appointment.equals(new String("test")));
    }

    @ParameterizedTest
    @CsvSource({
            "2023-06-20T09:00, 2023-06-20T10:00, 2023-06-20T09:00, 2023-06-20T10:00, true",
            "2023-06-20T09:00, 2023-06-20T10:00, 2023-06-20T09:00, 2023-06-20T11:00, false",
            "2023-06-20T09:00, 2023-06-20T10:00, 2023-06-20T08:00, 2023-06-20T10:00, false",
            "2023-06-20T09:00, 2023-06-20T10:00, 2023-06-21T09:00, 2023-06-21T10:00, false"
    })
    void testEqualsAllFields(String startDate1, String endDate1, String startDate2, String endDate2, boolean expected) {
        LocalDateTime start1 = LocalDateTime.parse(startDate1);
        LocalDateTime end1 = LocalDateTime.parse(endDate1);
        LocalDateTime start2 = LocalDateTime.parse(startDate2);
        LocalDateTime end2 = LocalDateTime.parse(endDate2);

        appointment.setStartDate(start1);
        appointment.setEndDate(end1);
        appointment.setEmployee(employee);
        appointment.setClient(client);
        appointment.setActivities(activities);

        Appointment anotherAppointment = new Appointment();
        anotherAppointment.setStartDate(start2);
        anotherAppointment.setEndDate(end2);
        anotherAppointment.setEmployee(employee);
        anotherAppointment.setClient(client);
        anotherAppointment.setActivities(activities);

        assertEquals(expected, appointment.equals(anotherAppointment));
    }
}
