package com.prime.rushhour.domain.role.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Size(min = 3, message = "Name should be at least 3 characters long")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Name should be alphanumeric with underscores only")
    private String name;

    public Role() {}
}
