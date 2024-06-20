package com.prime.rushhour.domain.appointment.entity;

import com.prime.rushhour.domain.activity.entity.Activity;
import com.prime.rushhour.domain.client.entity.Client;
import com.prime.rushhour.domain.employee.entity.Employee;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Represents an appointment in the system.
 *
 * An appointment has a start date, end date, employee, client, and activities.
 *
 * @author Filip
 * @version 1.0
 */
@Entity
public class Appointment {

    /**
     * Unique identifier for the appointment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Start date and time of the appointment, cannot be null.
     */
    @Column(nullable = false)
    private LocalDateTime startDate;

    /**
     * End date and time of the appointment, cannot be null.
     */
    @Column(nullable = false)
    private LocalDateTime endDate;

    /**
     * Employee assigned to the appointment, cannot be null.
     */
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    /**
     * Client associated with the appointment, cannot be null.
     */
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    /**
     * List of activities included in the appointment.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "appointment_activities", joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id"))
    private List<Activity> activities;

    /**
     * Creates a new appointment with uninitialized fields.
     */
    public Appointment() {}

    /**
     * Creates a new appointment with the specified start date, end date, employee, client, and activities.
     *
     * @param startDate start date and time of the appointment
     * @param endDate end date and time of the appointment
     * @param employee employee assigned to the appointment
     * @param client client associated with the appointment
     * @param activities list of activities included in the appointment
     */
    public Appointment(LocalDateTime startDate, LocalDateTime endDate, Employee employee, Client client, List<Activity> activities) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.employee = employee;
        this.client = client;
        this.activities = activities;
    }

    /**
     * Returns the unique identifier of the appointment.
     *
     * @return id of the appointment
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the appointment.
     *
     * @param id id of the appointment
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the start date and time of the appointment.
     *
     * @return start date and time of the appointment
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date and time of the appointment.
     *
     * @param startDate start date and time of the appointment
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * Returns the end date and time of the appointment.
     *
     * @return end date and time of the appointment
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date and time of the appointment.
     *
     * @param endDate end date and time of the appointment
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * Returns the employee assigned to the appointment.
     *
     * @return employee assigned to the appointment
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Sets the employee assigned to the appointment.
     *
     * @param employee employee assigned to the appointment
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * Returns the client associated with the appointment.
     *
     * @return client associated with the appointment
     */
    public Client getClient() {
        return client;
    }

    /**
     * Sets the client associated with the appointment.
     *
     * @param client client associated with the appointment
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Returns the list of activities included in the appointment.
     *
     * @return list of activities included in the appointment
     */
    public List<Activity> getActivities() {
        return activities;
    }

    /**
     * Sets the list of activities included in the appointment.
     *
     * @param activities list of activities included in the appointment
     */
    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", employee=" + employee +
                ", client=" + client +
                ", activities=" + activities +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(id, that.id) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(employee, that.employee) && Objects.equals(client, that.client) && Objects.equals(activities, that.activities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, employee, client, activities);
    }
}
