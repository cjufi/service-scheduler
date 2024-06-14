package com.prime.rushhour.domain.role.entity;

import jakarta.persistence.*;

/**
 * Represents a role in the system.
 *
 * A role has a unique identifier and a name.
 *
 * @author Filip
 * @version 1.0
 */
@Entity
public class Role {

    /**
     * Unique identifier for the role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    /**
     * Name of the role, must be unique and not null.
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Creates a new role with uninitialized fields.
     */
    public Role() {
    }

    /**
     * Creates a new role with the specified name.
     *
     * @param name name of the role
     */
    public Role(String name) {
        this.name = name;
    }

    /**
     * Returns the unique identifier of the role.
     *
     * @return id of the role
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the role.
     *
     * @param id id of the role
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the name of the role.
     *
     * @return name of the role
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the role.
     *
     * @param name name of the role
     */
    public void setName(String name) {
        this.name = name;
    }
}
