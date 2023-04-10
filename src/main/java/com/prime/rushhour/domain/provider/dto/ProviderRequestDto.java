package com.prime.rushhour.domain.provider.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Pattern(regexp = "^[a-zA-Z0/9]*$")
    String businessDomain,
    @NotBlank(message = "Phone is a mandatory field")
    @Pattern(regexp = "^\\+?[0/9]+$")
    String phone,
    @NotBlank(message = "Start Time is a mandatory field")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    LocalTime startTimeOfWorkingDay,
    @NotBlank(message = "End Time is a mandatory field")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @NotBlank(message = "Setting working days is a must")
    LocalTime endTimeOfWorkingDay,
    Set<DayOfWeek> workingDays) { }