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

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    @Convert(converter = DurationConverter.class)
    private Duration duration;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "activity_employees", joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employees;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "appointment_activities", joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "appointment_id"))
    private Set<Appointment> appointments;

    public Activity() {
    }

    public Activity(Long id, String name, BigDecimal price, Duration duration, Provider provider, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.provider = provider;
        this.employees = employees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }
}