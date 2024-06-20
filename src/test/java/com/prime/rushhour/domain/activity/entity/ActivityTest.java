package com.prime.rushhour.domain.activity.entity;

import com.prime.rushhour.domain.appointment.entity.Appointment;
import com.prime.rushhour.domain.employee.entity.Employee;
import com.prime.rushhour.domain.provider.entity.Provider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {

    Activity activity;
    Provider provider;
    List<Employee> employees;
    Set<Appointment> appointments;

    @BeforeEach
    void setUp() throws Exception {
        provider = new Provider();
        employees = new ArrayList<>();
        appointments = new HashSet<>();
        activity = new Activity();
    }

    @AfterEach
    void tearDown() throws Exception {
        activity = null;
    }

    @Test
    void testSetName() {
        activity.setName("Yoga");
        assertEquals("Yoga", activity.getName());
    }

    @Test
    void testSetPrice() {
        activity.setPrice(BigDecimal.valueOf(50.0));
        assertEquals(BigDecimal.valueOf(50.0), activity.getPrice());
    }

    @Test
    void testSetDuration() {
        activity.setDuration(Duration.ofHours(1));
        assertEquals(Duration.ofHours(1), activity.getDuration());
    }

    @Test
    void testSetProvider() {
        activity.setProvider(provider);
        assertEquals(provider, activity.getProvider());
    }

    @Test
    void testSetEmployees() {
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        employees.add(employee1);
        employees.add(employee2);
        activity.setEmployees(employees);
        assertEquals(employees, activity.getEmployees());
    }

    @Test
    void testSetAppointments() {
        Appointment appointment1 = new Appointment();
        Appointment appointment2 = new Appointment();
        appointments.add(appointment1);
        appointments.add(appointment2);
        activity.setAppointments(appointments);
        assertEquals(appointments, activity.getAppointments());
    }

    @Test
    void testToString() {
        activity.setName("Yoga");
        activity.setPrice(BigDecimal.valueOf(50.0));
        activity.setDuration(Duration.ofHours(1));
        activity.setProvider(provider);
        activity.setEmployees(employees);
        activity.setAppointments(appointments);

        String activityString = activity.toString();

        assertTrue(activityString.contains("Yoga"));
        assertTrue(activityString.contains("50.0"));
        assertTrue(activityString.contains("PT1H"));
        assertTrue(activityString.contains(provider.toString()));
        assertTrue(activityString.contains(employees.toString()));
        assertTrue(activityString.contains(appointments.toString()));
    }

    @Test
    void testEqualsSameAddress() {
        assertTrue(activity.equals(activity));
    }

    @Test
    void testEqualsNull() {
        assertFalse(activity.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(activity.equals(new String("test")));
    }

    @ParameterizedTest
    @CsvSource({
            "1, Yoga, 50.0, 1, 1, Yoga, 50.0, 1, true",
            "2, Yoga, 50.0, 1, 1, Yoga, 50.0, 1, false",
            "1, Pilates, 50.0, 1, 1, Yoga, 50.0, 1, false",
            "1, Yoga, 60.0, 1, 1, Yoga, 50.0, 1, false",
            "1, Yoga, 50.0, 2, 1, Yoga, 50.0, 1, false",
            "1, Yoga, 50.0, 1, 2, Yoga, 50.0, 1, false"
    })
    void testEqualsAllFields(Long id1, String name1, BigDecimal price1, long durationHours1,
                             Long id2, String name2, BigDecimal price2, long durationHours2, boolean expected) {
        activity.setId(id1);
        activity.setName(name1);
        activity.setPrice(price1);
        activity.setDuration(Duration.ofHours(durationHours1));
        activity.setProvider(provider);
        activity.setEmployees(employees);
        activity.setAppointments(appointments);

        Activity anotherActivity = new Activity();
        anotherActivity.setId(id2);
        anotherActivity.setName(name2);
        anotherActivity.setPrice(price2);
        anotherActivity.setDuration(Duration.ofHours(durationHours2));
        anotherActivity.setProvider(provider);
        anotherActivity.setEmployees(employees);
        anotherActivity.setAppointments(appointments);

        assertEquals(expected, activity.equals(anotherActivity));
    }
}
