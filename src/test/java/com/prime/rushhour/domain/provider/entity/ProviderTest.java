package com.prime.rushhour.domain.provider.entity;

import com.prime.rushhour.domain.activity.entity.Activity;
import com.prime.rushhour.domain.employee.entity.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProviderTest {

    Provider provider;
    Set<Employee> employees;
    Set<Activity> activities;
    Set<DayOfWeek> workingDays;

    @BeforeEach
    void setUp() {
        employees = new HashSet<>();
        activities = new HashSet<>();
        workingDays = new HashSet<>();
        provider = new Provider();
    }

    @AfterEach
    void tearDown() {
        provider = null;
    }

    @Test
    void testSetName() {
        String name = "ProviderName";
        provider.setName(name);
        assertEquals(name, provider.getName());
    }

    @Test
    void testSetWebsite() {
        String website = "http://example.com";
        provider.setWebsite(website);
        assertEquals(website, provider.getWebsite());
    }

    @Test
    void testSetBusinessDomain() {
        String businessDomain = "example.com";
        provider.setBusinessDomain(businessDomain);
        assertEquals(businessDomain, provider.getBusinessDomain());
    }

    @Test
    void testSetPhone() {
        String phone = "123-456-7890";
        provider.setPhone(phone);
        assertEquals(phone, provider.getPhone());
    }

    @Test
    void testSetStartTimeOfWorkingDay() {
        LocalTime startTime = LocalTime.of(9, 0);
        provider.setStartTimeOfWorkingDay(startTime);
        assertEquals(startTime, provider.getStartTimeOfWorkingDay());
    }

    @Test
    void testSetEndTimeOfWorkingDay() {
        LocalTime endTime = LocalTime.of(17, 0);
        provider.setEndTimeOfWorkingDay(endTime);
        assertEquals(endTime, provider.getEndTimeOfWorkingDay());
    }

    @Test
    void testSetWorkingDays() {
        workingDays.add(DayOfWeek.MONDAY);
        workingDays.add(DayOfWeek.TUESDAY);
        provider.setWorkingDays(workingDays);
        assertEquals(workingDays, provider.getWorkingDays());
    }

    @Test
    void testSetEmployees() {
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        employees.add(employee1);
        employees.add(employee2);
        provider.setEmployees(employees);
        assertEquals(employees, provider.getEmployees());
    }

    @Test
    void testSetActivities() {
        Activity activity1 = new Activity();
        Activity activity2 = new Activity();
        activities.add(activity1);
        activities.add(activity2);
        provider.setActivities(activities);
        assertEquals(activities, provider.getActivities());
    }

    @Test
    void testToString() {
        String name = "ProviderName";
        String website = "http://example.com";
        String businessDomain = "example.com";
        String phone = "123-456-7890";
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        workingDays.add(DayOfWeek.MONDAY);
        workingDays.add(DayOfWeek.TUESDAY);
        provider.setName(name);
        provider.setWebsite(website);
        provider.setBusinessDomain(businessDomain);
        provider.setPhone(phone);
        provider.setStartTimeOfWorkingDay(startTime);
        provider.setEndTimeOfWorkingDay(endTime);
        provider.setWorkingDays(workingDays);
        provider.setEmployees(employees);
        provider.setActivities(activities);

        String providerString = provider.toString();

        assertTrue(providerString.contains(name));
        assertTrue(providerString.contains(website));
        assertTrue(providerString.contains(businessDomain));
        assertTrue(providerString.contains(phone));
        assertTrue(providerString.contains(startTime.toString()));
        assertTrue(providerString.contains(endTime.toString()));
        assertTrue(providerString.contains(workingDays.toString()));
        assertTrue(providerString.contains(employees.toString()));
        assertTrue(providerString.contains(activities.toString()));
    }

    @Test
    void testEqualsSameProvider() {
        assertTrue(provider.equals(provider));
    }

    @Test
    void testEqualsNull() {
        assertFalse(provider.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(provider.equals(new String("test")));
    }

    @ParameterizedTest
    @CsvSource({
            "ProviderName, http://example.com, example.com, 123-456-7890, 09:00, 17:00, ProviderName, http://example.com, example.com, 123-456-7890, 09:00, 17:00, true",
            "ProviderName, http://example.com, example.com, 123-456-7890, 09:00, 17:00, ProviderName, http://example.com, example.com, 123-456-7890, 08:00, 17:00, false",
            "ProviderName, http://example.com, example.com, 123-456-7890, 09:00, 17:00, ProviderName, http://example.com, example.org, 123-456-7890, 09:00, 17:00, false",
            "ProviderName, http://example.com, example.com, 123-456-7890, 09:00, 17:00, ProviderX, http://example.com, example.com, 123-456-7890, 09:00, 17:00, false"
    })
    void testEqualsAllFields(String name1, String website1, String businessDomain1, String phone1,
                             String startTime1, String endTime1,
                             String name2, String website2, String businessDomain2, String phone2,
                             String startTime2, String endTime2, boolean expected) {
        provider.setName(name1);
        provider.setWebsite(website1);
        provider.setBusinessDomain(businessDomain1);
        provider.setPhone(phone1);
        provider.setStartTimeOfWorkingDay(LocalTime.parse(startTime1));
        provider.setEndTimeOfWorkingDay(LocalTime.parse(endTime1));
        provider.setWorkingDays(workingDays);
        provider.setEmployees(employees);
        provider.setActivities(activities);

        Provider anotherProvider = new Provider();
        anotherProvider.setName(name2);
        anotherProvider.setWebsite(website2);
        anotherProvider.setBusinessDomain(businessDomain2);
        anotherProvider.setPhone(phone2);
        anotherProvider.setStartTimeOfWorkingDay(LocalTime.parse(startTime2));
        anotherProvider.setEndTimeOfWorkingDay(LocalTime.parse(endTime2));
        anotherProvider.setWorkingDays(workingDays);
        anotherProvider.setEmployees(employees);
        anotherProvider.setActivities(activities);

        assertEquals(expected, provider.equals(anotherProvider));
    }
}
