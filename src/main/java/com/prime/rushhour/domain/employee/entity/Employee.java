package com.prime.rushhour.domain.employee.entity;

import com.prime.rushhour.domain.account.entity.Account;
import com.prime.rushhour.domain.provider.entity.Provider;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private Double ratePerHour;

    @Column(nullable = false)
    private LocalDate hireDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    public Employee(String title, String phone, Double ratePerHour, LocalDate hireDate, Account account, Provider provider) {
        this.title = title;
        this.phone = phone;
        this.ratePerHour = ratePerHour;
        this.hireDate = hireDate;
        this.account = account;
        this.provider = provider;
    }

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getRatePerHour() {
        return ratePerHour;
    }

    public void setRatePerHour(Double ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}