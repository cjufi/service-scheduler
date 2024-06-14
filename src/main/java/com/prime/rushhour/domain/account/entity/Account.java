package com.prime.rushhour.domain.account.entity;

import com.prime.rushhour.domain.role.entity.Role;
import jakarta.persistence.*;

/**
 * Represents an account in the system.
 *
 * An account has an email, full name, password, and role.
 *
 * @author Filip
 * @version 1.0
 */
@Entity
public class Account {

    /**
     * Unique identifier for the account.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Email of the account, must be unique and not null.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Full name of the account holder, cannot be null.
     */
    @Column(nullable = false)
    private String fullName;

    /**
     * Password for the account, cannot be null.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Role associated with the account, cannot be null.
     */
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    /**
     * Creates a new account with the specified email, full name, password, and role.
     *
     * @param email email of the account holder
     * @param fullName full name of the account holder
     * @param password password for the account
     * @param role role of the account
     */
    public Account(String email, String fullName, String password, Role role) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.role = role;
    }

    /**
     * Creates a new account with uninitialized fields.
     */
    public Account() {}

    /**
     * Returns the unique identifier of the account.
     *
     * @return id of the account
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the account.
     *
     * @param id id of the account
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the email of the account.
     *
     * @return email of the account
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the account.
     *
     * @param email email of the account
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the full name of the account holder.
     *
     * @return full name of the account holder
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the full name of the account holder.
     *
     * @param fullName full name of the account holder
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Returns the password of the account.
     *
     * @return password of the account
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the account.
     *
     * @param password password of the account
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the role associated with the account.
     *
     * @return role of the account
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the role associated with the account.
     *
     * @param role role of the account
     */
    public void setRole(Role role) {
        this.role = role;
    }
}
