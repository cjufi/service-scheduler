package com.prime.rushhour.domain.employee.entity;

import com.prime.rushhour.domain.account.entity.Account;
import com.prime.rushhour.domain.appointment.entity.Appointment;
import com.prime.rushhour.domain.provider.entity.Provider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    Employee employee;
    Account account;
    Provider provider;
    Set<Appointment> appointments;

    @BeforeEach
    void setUp() {
        account = new Account();
        provider = new Provider();
        appointments = new HashSet<>();
        employee = new Employee();
    }

    @AfterEach
    void tearDown() {
        employee = null;
    }

    @Test
    void testSetTitle() {
        String title = "Manager";
        employee.setTitle(title);
        assertEquals(title, employee.getTitle());
    }

    @Test
    void testSetPhone() {
        String phone = "123-456-7890";
        employee.setPhone(phone);
        assertEquals(phone, employee.getPhone());
    }

    @Test
    void testSetRatePerHour() {
        Double ratePerHour = 25.0;
        employee.setRatePerHour(ratePerHour);
        assertEquals(ratePerHour, employee.getRatePerHour());
    }

    @Test
    void testSetHireDate() {
        LocalDate hireDate = LocalDate.of(2023, 1, 1);
        employee.setHireDate(hireDate);
        assertEquals(hireDate, employee.getHireDate());
    }

    @Test
    void testSetAccount() {
        employee.setAccount(account);
        assertEquals(account, employee.getAccount());
    }

    @Test
    void testSetProvider() {
        employee.setProvider(provider);
        assertEquals(provider, employee.getProvider());
    }

    @Test
    void testSetAppointments() {
        Appointment appointment1 = new Appointment();
        Appointment appointment2 = new Appointment();
        appointments.add(appointment1);
        appointments.add(appointment2);
        employee.setAppointments(appointments);
        assertEquals(appointments, employee.getAppointments());
    }

    @Test
    void testToString() {
        String title = "Manager";
        String phone = "123-456-7890";
        Double ratePerHour = 25.0;
        LocalDate hireDate = LocalDate.of(2023, 1, 1);
        employee.setTitle(title);
        employee.setPhone(phone);
        employee.setRatePerHour(ratePerHour);
        employee.setHireDate(hireDate);
        employee.setAccount(account);
        employee.setProvider(provider);
        employee.setAppointments(appointments);

        String employeeString = employee.toString();

        assertTrue(employeeString.contains(title));
        assertTrue(employeeString.contains(phone));
        assertTrue(employeeString.contains(ratePerHour.toString()));
        assertTrue(employeeString.contains(hireDate.toString()));
        assertTrue(employeeString.contains(account.toString()));
        assertTrue(employeeString.contains(provider.toString()));
        assertTrue(employeeString.contains(appointments.toString()));
    }

    @Test
    void testEqualsSameEmployee() {
        assertTrue(employee.equals(employee));
    }

    @Test
    void testEqualsNull() {
        assertFalse(employee.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(employee.equals(new String("test")));
    }

    @ParameterizedTest
    @CsvSource({
            "Manager, 123-456-7890, 25.0, 2023-01-01, Manager, 123-456-7890, 25.0, 2023-01-01, true",
            "Manager, 123-456-7890, 25.0, 2023-01-01, Manager, 123-456-7890, 30.0, 2023-01-01, false",
            "Manager, 123-456-7890, 25.0, 2023-01-01, Manager, 987-654-3210, 25.0, 2023-01-01, false",
            "Manager, 123-456-7890, 25.0, 2023-01-01, Assistant, 123-456-7890, 25.0, 2023-01-01, false"
    })
    void testEqualsAllFields(String title1, String phone1, Double ratePerHour1, String hireDate1,
                             String title2, String phone2, Double ratePerHour2, String hireDate2, boolean expected) {
        employee.setTitle(title1);
        employee.setPhone(phone1);
        employee.setRatePerHour(ratePerHour1);
        employee.setHireDate(LocalDate.parse(hireDate1));
        employee.setAccount(account);
        employee.setProvider(provider);
        employee.setAppointments(appointments);

        Employee anotherEmployee = new Employee();
        anotherEmployee.setTitle(title2);
        anotherEmployee.setPhone(phone2);
        anotherEmployee.setRatePerHour(ratePerHour2);
        anotherEmployee.setHireDate(LocalDate.parse(hireDate2));
        anotherEmployee.setAccount(account);
        anotherEmployee.setProvider(provider);
        anotherEmployee.setAppointments(appointments);

        assertEquals(expected, employee.equals(anotherEmployee));
    }
}
