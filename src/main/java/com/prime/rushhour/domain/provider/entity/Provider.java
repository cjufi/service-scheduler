package com.prime.rushhour.domain.provider.entity;

import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Entity
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String website;

    @Column(nullable = false)
    private String businessDomain;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private LocalTime startTimeOfWorkingDay;

    @Column(nullable = false)
    private LocalTime endTimeOfWorkingDay;

    @ElementCollection
    @CollectionTable(name = "working_days", joinColumns = @JoinColumn(name = "provider_id"))
    @Column(name = "working_day", nullable = false)
    private Set<DayOfWeek> workingDays;

    public Provider() {
    }

    public Provider(String name, String website, String businessDomain, String phone, LocalTime startTimeOfWorkingDay, LocalTime endTimeOfWorkingDay, Set<DayOfWeek> workingDays) {
        this.name = name;
        this.website = website;
        this.businessDomain = businessDomain;
        this.phone = phone;
        this.startTimeOfWorkingDay = startTimeOfWorkingDay;
        this.endTimeOfWorkingDay = endTimeOfWorkingDay;
        this.workingDays = workingDays;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBusinessDomain() {
        return businessDomain;
    }

    public void setBusinessDomain(String businessDomain) {
        this.businessDomain = businessDomain;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalTime getStartTimeOfWorkingDay() {
        return startTimeOfWorkingDay;
    }

    public void setStartTimeOfWorkingDay(LocalTime startTimeOfWorkingDay) {
        this.startTimeOfWorkingDay = startTimeOfWorkingDay;
    }

    public LocalTime getEndTimeOfWorkingDay() {
        return endTimeOfWorkingDay;
    }

    public void setEndTimeOfWorkingDay(LocalTime endTimeOfWorkingDay) {
        this.endTimeOfWorkingDay = endTimeOfWorkingDay;
    }

    public Set<DayOfWeek> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(Set<DayOfWeek> workingDays) {
        this.workingDays = workingDays;
    }
}