package com.prime.rushhour.domain.provider.entity;

import com.prime.rushhour.domain.activity.entity.Activity;
import com.prime.rushhour.domain.employee.entity.Employee;
import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

/**
 * Represents a provider in the system.
 *
 * A provider has a name, website, business domain, phone number, working hours, and related employees and activities.
 *
 * @author Filip
 * @version 1.0
 */
@Entity
public class Provider {

    /**
     * Unique identifier for the provider.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    /**
     * Name of the provider, must be unique and not null.
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Website of the provider, cannot be null.
     */
    @Column(nullable = false)
    private String website;

    /**
     * Business domain of the provider, must be unique and not null.
     */
    @Column(nullable = false, unique = true)
    private String businessDomain;

    /**
     * Phone number of the provider, cannot be null.
     */
    @Column(nullable = false)
    private String phone;

    /**
     * Start time of the provider's working day, cannot be null.
     */
    @Column(nullable = false)
    private LocalTime startTimeOfWorkingDay;

    /**
     * End time of the provider's working day, cannot be null.
     */
    @Column(nullable = false)
    private LocalTime endTimeOfWorkingDay;

    /**
     * Working days of the provider, cannot be null.
     */
    @ElementCollection
    @CollectionTable(name = "working_days", joinColumns = @JoinColumn(name = "provider_id"))
    @Column(name = "working_day", nullable = false)
    private Set<DayOfWeek> workingDays;

    /**
     * Employees associated with the provider.
     */
    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Employee> employees;

    /**
     * Activities associated with the provider.
     */
    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Activity> activities;

    /**
     * Creates a new provider with uninitialized fields.
     */
    public Provider() {
    }

    /**
     * Creates a new provider with the specified name, website, business domain, phone number, working hours, and working days.
     *
     * @param name                  name of the provider
     * @param website               website of the provider
     * @param businessDomain        business domain of the provider
     * @param phone                 phone number of the provider
     * @param startTimeOfWorkingDay start time of the provider's working day
     * @param endTimeOfWorkingDay   end time of the provider's working day
     * @param workingDays           working days of the provider
     */
    public Provider(String name, String website, String businessDomain, String phone, LocalTime startTimeOfWorkingDay, LocalTime endTimeOfWorkingDay, Set<DayOfWeek> workingDays) {
        this.name = name;
        this.website = website;
        this.businessDomain = businessDomain;
        this.phone = phone;
        this.startTimeOfWorkingDay = startTimeOfWorkingDay;
        this.endTimeOfWorkingDay = endTimeOfWorkingDay;
        this.workingDays = workingDays;
    }

    /**
     * Returns the unique identifier of the provider.
     *
     * @return id of the provider
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the provider.
     *
     * @param id id of the provider
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the name of the provider.
     *
     * @return name of the provider
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the provider.
     *
     * @param name name of the provider
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the website of the provider.
     *
     * @return website of the provider
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Sets the website of the provider.
     *
     * @param website website of the provider
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * Returns the business domain of the provider.
     *
     * @return business domain of the provider
     */
    public String getBusinessDomain() {
        return businessDomain;
    }

    /**
     * Sets the business domain of the provider.
     *
     * @param businessDomain business domain of the provider
     */
    public void setBusinessDomain(String businessDomain) {
        this.businessDomain = businessDomain;
    }

    /**
     * Returns the phone number of the provider.
     *
     * @return phone number of the provider
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the provider.
     *
     * @param phone phone number of the provider
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the start time of the provider's working day.
     *
     * @return start time of the provider's working day
     */
    public LocalTime getStartTimeOfWorkingDay() {
        return startTimeOfWorkingDay;
    }

    /**
     * Sets the start time of the provider's working day.
     *
     * @param startTimeOfWorkingDay start time of the provider's working day
     */
    public void setStartTimeOfWorkingDay(LocalTime startTimeOfWorkingDay) {
        this.startTimeOfWorkingDay = startTimeOfWorkingDay;
    }

    /**
     * Returns the end time of the provider's working day.
     *
     * @return end time of the provider's working day
     */
    public LocalTime getEndTimeOfWorkingDay() {
        return endTimeOfWorkingDay;
    }

    /**
     * Sets the end time of the provider's working day.
     *
     * @param endTimeOfWorkingDay end time of the provider's working day
     */
    public void setEndTimeOfWorkingDay(LocalTime endTimeOfWorkingDay) {
        this.endTimeOfWorkingDay = endTimeOfWorkingDay;
    }

    /**
     * Returns the working days of the provider.
     *
     * @return working days of the provider
     */
    public Set<DayOfWeek> getWorkingDays() {
        return workingDays;
    }

    /**
     * Sets the working days of the provider.
     *
     * @param workingDays working days of the provider
     */
    public void setWorkingDays(Set<DayOfWeek> workingDays) {
        this.workingDays = workingDays;
    }

    /**
     * Returns the employees associated with the provider.
     *
     * @return employees associated with the provider
     */
    public Set<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the employees associated with the provider.
     *
     * @param employees employees associated with the provider
     */
    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Returns the activities associated with the provider.
     *
     * @return activities associated with the provider
     */
    public Set<Activity> getActivities() {
        return activities;
    }

    /**
     * Sets the activities associated with the provider.
     *
     * @param activities activities associated with the provider
     */
    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }
}
