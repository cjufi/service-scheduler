package com.prime.rushhour.domain.client.entity;

import com.prime.rushhour.domain.account.entity.Account;
import com.prime.rushhour.domain.appointment.entity.Appointment;
import jakarta.persistence.*;

import java.util.Set;

/**
 * Represents a client in the system.
 *
 * A client has a phone number, address, account, and appointments.
 *
 * @author Filip
 * @version 1.0
 */
@Entity
public class Client {

    /**
     * Unique identifier for the client.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Phone number of the client, cannot be null.
     */
    @Column(nullable = false)
    private String phone;

    /**
     * Address of the client, cannot be null.
     */
    @Column(nullable = false)
    private String address;

    /**
     * Account associated with the client, cannot be null.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    /**
     * Appointments associated with the client.
     */
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Appointment> appointments;

    /**
     * Creates a new client with uninitialized fields.
     */
    public Client() {
    }

    /**
     * Creates a new client with the specified phone number, address, and account.
     *
     * @param phone   phone number of the client
     * @param address address of the client
     * @param account account associated with the client
     */
    public Client(String phone, String address, Account account) {
        this.phone = phone;
        this.address = address;
        this.account = account;
    }

    /**
     * Returns the unique identifier of the client.
     *
     * @return id of the client
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the client.
     *
     * @param id id of the client
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the phone number of the client.
     *
     * @return phone number of the client
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the client.
     *
     * @param phone phone number of the client
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the address of the client.
     *
     * @return address of the client
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the client.
     *
     * @param address address of the client
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the account associated with the client.
     *
     * @return account associated with the client
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Sets the account associated with the client.
     *
     * @param account account associated with the client
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Returns the appointments associated with the client.
     *
     * @return appointments associated with the client
     */
    public Set<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Sets the appointments associated with the client.
     *
     * @param appointments appointments associated with the client
     */
    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }
}
