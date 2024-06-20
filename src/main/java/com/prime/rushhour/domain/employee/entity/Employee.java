package com.prime.rushhour.domain.employee.entity;

import com.prime.rushhour.domain.account.entity.Account;
import com.prime.rushhour.domain.appointment.entity.Appointment;
import com.prime.rushhour.domain.provider.entity.Provider;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * Represents an employee in the system.
 *
 * An employee has a title, phone number, rate per hour, hire date, account, provider, and appointments.
 *
 * @author Filip
 * @version 1.0
 */
@Entity
public class Employee {

    /**
     * Unique identifier for the employee.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Title of the employee, cannot be null.
     */
    @Column(nullable = false)
    private String title;

    /**
     * Phone number of the employee, cannot be null.
     */
    @Column(nullable = false)
    private String phone;

    /**
     * Hourly rate of the employee, cannot be null.
     */
    @Column(nullable = false)
    private Double ratePerHour;

    /**
     * Hire date of the employee, cannot be null.
     */
    @Column(nullable = false)
    private LocalDate hireDate;

    /**
     * Account associated with the employee, cannot be null.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    /**
     * Provider associated with the employee, cannot be null.
     */
    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    /**
     * Appointments associated with the employee.
     */
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Appointment> appointments;

    /**
     * Creates a new employee with the specified title, phone number, rate per hour, hire date, account, and provider.
     *
     * @param title      title of the employee
     * @param phone      phone number of the employee
     * @param ratePerHour hourly rate of the employee
     * @param hireDate   hire date of the employee
     * @param account    account associated with the employee
     * @param provider   provider associated with the employee
     */
    public Employee(String title, String phone, Double ratePerHour, LocalDate hireDate, Account account, Provider provider) {
        this.title = title;
        this.phone = phone;
        this.ratePerHour = ratePerHour;
        this.hireDate = hireDate;
        this.account = account;
        this.provider = provider;
    }

    /**
     * Creates a new employee with uninitialized fields.
     */
    public Employee() {
    }

    /**
     * Returns the unique identifier of the employee.
     *
     * @return id of the employee
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the employee.
     *
     * @param id id of the employee
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the title of the employee.
     *
     * @return title of the employee
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the employee.
     *
     * @param title title of the employee
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the phone number of the employee.
     *
     * @return phone number of the employee
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the employee.
     *
     * @param phone phone number of the employee
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the hourly rate of the employee.
     *
     * @return hourly rate of the employee
     */
    public Double getRatePerHour() {
        return ratePerHour;
    }

    /**
     * Sets the hourly rate of the employee.
     *
     * @param ratePerHour hourly rate of the employee
     */
    public void setRatePerHour(Double ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    /**
     * Returns the hire date of the employee.
     *
     * @return hire date of the employee
     */
    public LocalDate getHireDate() {
        return hireDate;
    }

    /**
     * Sets the hire date of the employee.
     *
     * @param hireDate hire date of the employee
     */
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    /**
     * Returns the account associated with the employee.
     *
     * @return account associated with the employee
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Sets the account associated with the employee.
     *
     * @param account account associated with the employee
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Returns the provider associated with the employee.
     *
     * @return provider associated with the employee
     */
    public Provider getProvider() {
        return provider;
    }

    /**
     * Sets the provider associated with the employee.
     *
     * @param provider provider associated with the employee
     */
    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    /**
     * Returns the appointments associated with the employee.
     *
     * @return appointments associated with the employee
     */
    public Set<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Sets the appointments associated with the employee.
     *
     * @param appointments appointments associated with the employee
     */
    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", phone='" + phone + '\'' +
                ", ratePerHour=" + ratePerHour +
                ", hireDate=" + hireDate +
                ", account=" + account +
                ", provider=" + provider +
                ", appointments=" + appointments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(title, employee.title) && Objects.equals(phone, employee.phone) && Objects.equals(ratePerHour, employee.ratePerHour) && Objects.equals(hireDate, employee.hireDate) && Objects.equals(account, employee.account) && Objects.equals(provider, employee.provider) && Objects.equals(appointments, employee.appointments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, phone, ratePerHour, hireDate, account, provider, appointments);
    }
}
