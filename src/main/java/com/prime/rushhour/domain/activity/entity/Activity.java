package com.prime.rushhour.domain.activity.entity;

import com.prime.rushhour.domain.activity.repository.converter.DurationConverter;
import com.prime.rushhour.domain.appointment.entity.Appointment;
import com.prime.rushhour.domain.employee.entity.Employee;
import com.prime.rushhour.domain.provider.entity.Provider;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Set;

/**
 * Represents an activity in the system.
 *
 * An activity has a name, price, duration, provider, employees, and appointments.
 *
 * @author Filip
 * @version 1.0
 */
@Entity
public class Activity {

    /**
     * Unique identifier for the activity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the activity, cannot be null.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Price of the activity, cannot be null.
     */
    @Column(nullable = false)
    private BigDecimal price;

    /**
     * Duration of the activity, cannot be null.
     */
    @Column(nullable = false)
    @Convert(converter = DurationConverter.class)
    private Duration duration;

    /**
     * Provider associated with the activity, cannot be null.
     */
    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    /**
     * List of employees associated with the activity.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "activity_employees", joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employees;

    /**
     * Set of appointments associated with the activity.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "appointment_activities", joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "appointment_id"))
    private Set<Appointment> appointments;

    /**
     * Creates a new activity with uninitialized fields.
     */
    public Activity() {}

    /**
     * Creates a new activity with the specified id, name, price, duration, provider, and employees.
     *
     * @param id unique identifier of the activity
     * @param name name of the activity
     * @param price price of the activity
     * @param duration duration of the activity
     * @param provider provider associated with the activity
     * @param employees list of employees associated with the activity
     */
    public Activity(Long id, String name, BigDecimal price, Duration duration, Provider provider, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.provider = provider;
        this.employees = employees;
    }

    /**
     * Returns the unique identifier of the activity.
     *
     * @return id of the activity
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the activity.
     *
     * @param id id of the activity
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the name of the activity.
     *
     * @return name of the activity
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the activity.
     *
     * @param name name of the activity
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the price of the activity.
     *
     * @return price of the activity
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the price of the activity.
     *
     * @param price price of the activity
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Returns the duration of the activity.
     *
     * @return duration of the activity
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the activity.
     *
     * @param duration duration of the activity
     */
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    /**
     * Returns the provider associated with the activity.
     *
     * @return provider associated with the activity
     */
    public Provider getProvider() {
        return provider;
    }

    /**
     * Sets the provider associated with the activity.
     *
     * @param provider provider associated with the activity
     */
    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    /**
     * Returns the list of employees associated with the activity.
     *
     * @return list of employees associated with the activity
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees associated with the activity.
     *
     * @param employees list of employees associated with the activity
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Returns the set of appointments associated with the activity.
     *
     * @return set of appointments associated with the activity
     */
    public Set<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Sets the set of appointments associated with the activity.
     *
     * @param appointments set of appointments associated with the activity
     */
    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }
}
