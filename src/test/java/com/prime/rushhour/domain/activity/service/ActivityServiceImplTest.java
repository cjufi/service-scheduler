package com.prime.rushhour.domain.activity.service;

import com.prime.rushhour.domain.account.entity.Account;
import com.prime.rushhour.domain.activity.dto.ActivityRequest;
import com.prime.rushhour.domain.activity.dto.ActivityResponse;
import com.prime.rushhour.domain.activity.entity.Activity;
import com.prime.rushhour.domain.activity.mapper.ActivityMapper;
import com.prime.rushhour.domain.activity.repository.ActivityRepository;
import com.prime.rushhour.domain.employee.entity.Employee;
import com.prime.rushhour.domain.provider.dto.ProviderResponse;
import com.prime.rushhour.domain.provider.entity.Provider;
import com.prime.rushhour.domain.role.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActivityServiceImplTest {

    @InjectMocks
    private ActivityServiceImpl activityService;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private ActivityMapper activityMapper;

    private ActivityRequest activityRequest;

    private Activity activity;

    private ActivityResponse activityResponse;

    @BeforeEach
    void setUp() {

        ActivityRequest activityRequest = new ActivityRequest(
                "Code Review", BigDecimal.valueOf(20),
                240L, 1L, List.of(1L, 2L));

        ActivityResponse activityResponse = new ActivityResponse(
                "Code Review", BigDecimal.valueOf(20),
                240L, new ProviderResponse( "Prime Software", "https://filip.com",
                "ft12", "+3816555333",
                LocalTime.of(8,0,0), LocalTime.of(17,0,0),
                Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY)), List.of(1L, 2L));

        Activity activity = new Activity(
                1L, "Code Review", BigDecimal.valueOf(20), Duration.ofMinutes(240),
                new Provider( "Filip Massage", "https://filip.com", "ft12", "+3816555333",
                        LocalTime.of(8,0,0), LocalTime.of(17,0,0),
                        Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY)),
                List.of(
                        new Employee("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                                new Account("ftasic39@gmail.com", "FilipTasic", "Password12#", new Role("PROVIDER_ADMIN")),
                                new Provider("Filip Massage", "https://filip.com", "gmail.com", "+3816555333",
                                        LocalTime.of(8, 0), LocalTime.of(15,0), Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY)))
                ));

    }

    @Test
    void save() {
        when(activityMapper.toEntity(activityRequest)).thenReturn(activity);
        when(activityRepository.save(activity)).thenReturn(activity);
        when(activityMapper.toDto(activity)).thenReturn(activityResponse);

        ActivityResponse result = activityService.save(activityRequest);

        assertEquals(activityResponse, result);
    }

    @Test
    void getById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }
}