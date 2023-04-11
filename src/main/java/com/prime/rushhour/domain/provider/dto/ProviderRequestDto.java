package com.prime.rushhour.domain.provider.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

public record ProviderRequestDto(
    @NotBlank(message = "Name is a mandatory field")
    @Column(unique = true)
    @Size(min = 3, message = "Name must be at least 3 characters long")
    String name,
    @URL
    @NotBlank(message = "Website url is a mandatory field")
    String website,
    @NotBlank(message = "Domain is a mandatory field")
    @Column(unique = true)
    @Size(min = 2, message = "Domain must be at least 2 characters long")
    @Pattern(regexp = "^[a-zA-Z\\d]*$")
    String businessDomain,
    @NotBlank(message = "Phone is a mandatory field")
    @Pattern(regexp = "^\\+?\\d+$")
    String phone,
    @NotNull(message = "Start Time is a mandatory field")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    LocalTime startTimeOfWorkingDay,
    @NotNull(message = "End Time is a mandatory field")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    LocalTime endTimeOfWorkingDay,
    @NotNull(message = "Setting working days is a must")
    Set<DayOfWeek> workingDays) { }