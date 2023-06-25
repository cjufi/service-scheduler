package com.prime.rushhour.domain.activity.service;

import com.prime.rushhour.domain.account.entity.Account;
import com.prime.rushhour.domain.activity.dto.ActivityRequest;
import com.prime.rushhour.domain.activity.dto.ActivityResponse;
import com.prime.rushhour.domain.activity.entity.Activity;
import com.prime.rushhour.domain.activity.mapper.ActivityMapper;
import com.prime.rushhour.domain.activity.repository.ActivityRepository;
import com.prime.rushhour.domain.employee.entity.Employee;
import com.prime.rushhour.domain.employee.service.EmployeeService;
import com.prime.rushhour.domain.provider.dto.ProviderResponse;
import com.prime.rushhour.domain.provider.entity.Provider;
import com.prime.rushhour.domain.role.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityServiceImplTest {

    @InjectMocks
    private ActivityServiceImpl activityService;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private ActivityMapper activityMapper;

    @Mock
    private EmployeeService employeeService;

    private ActivityRequest activityRequest;

    private Activity activity;

    private ActivityResponse activityResponse;

    @BeforeEach
    void setUp() {

        activityRequest = new ActivityRequest(
                "Code Review", BigDecimal.valueOf(20),
                240L, 1L, List.of(1L, 2L));

        activityResponse = new ActivityResponse(
                "Code Review", BigDecimal.valueOf(20),
                240L, new ProviderResponse("Prime Software", "https://filip.com",
                "ft12", "+3816555333",
                LocalTime.of(8, 0, 0), LocalTime.of(17, 0, 0),
                Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY)), List.of(1L, 2L));

        activity = new Activity(
                1L, "Code Review", BigDecimal.valueOf(20), Duration.ofMinutes(240),
                new Provider("Filip Massage", "https://filip.com", "ft12", "+3816555333",
                        LocalTime.of(8, 0, 0), LocalTime.of(17, 0, 0),
                        Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY)),
                List.of(
                        new Employee("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                                new Account("ftasic39@gmail.com", "FilipTasic", "Password12#", new Role("PROVIDER_ADMIN")),
                                new Provider("Filip Massage", "https://filip.com", "gmail.com", "+3816555333",
                                        LocalTime.of(8, 0), LocalTime.of(15, 0), Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY)))
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
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));
        when(activityMapper.toDto(activity)).thenReturn(activityResponse);

        var result = activityService.getById(1L);

        assertEquals("Code Review", result.name());
    }

    @Test
    void getAll() {

        List<Activity> activities = List.of(activity);

        Page<Activity> page = new PageImpl<>(activities);

        when(activityRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(activityMapper.toDto(any(Activity.class))).thenReturn(activityResponse);

        Page<ActivityResponse> result = activityService.getAll(PageRequest.of(0, 10));

        assertEquals(1, result.getSize());
        assertEquals("Code Review", result.getContent().get(0).name());
    }

    @Test
    void delete() {
        when(activityRepository.existsById(1L)).thenReturn(true);
        activityService.delete(1L);
        verify(activityRepository, times(1)).deleteById(1L);
    }

    @Test
    void update() {
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));
        when(activityRepository.save(activity)).thenReturn(activity);
        when(activityMapper.toDto(activity)).thenReturn(activityResponse);

        var result = activityService.update(1L, activityRequest);

        assertEquals(result, activityResponse);

        verify(activityMapper).update(activity, activityRequest);
    }

    @Test
    void deleteByProviderId() {
        activityService.deleteByProviderId(1L);

        verify(activityRepository, times(1)).deleteActivitiesByProviderId(1L);
    }

    @Test
    void getProviderIdFromActivityId() {
        activityService.getProviderIdFromActivityId(1L);

        verify(activityRepository, times(1)).findProviderIdByActivityId(1L);
    }

    @Test
    void IsEmployeesActivitySame() {
        Long accountId = 1L;
        Long providerId = 1L;
        List<Long> activityIds = List.of(1L, 2L);

        when(employeeService.getProviderIdFromAccount(accountId)).thenReturn(providerId);
        when(activityRepository.findProviderIdByActivityId(anyLong())).thenReturn(providerId);

        boolean result = activityService.isEmployeesActivitySame(activityIds, accountId);

        assertTrue(result);
        verify(employeeService).getProviderIdFromAccount(accountId);
        verify(activityRepository, times(2)).findProviderIdByActivityId(anyLong());
    }

    @Test
    void addPricesOfActivities_ReturnsZero() {
        List<Activity> activities = List.of();

        BigDecimal result = activityService.addPricesOfActivities(activities);

        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    void testAddPricesOfActivities_ReturnsValue() {
        List<Activity> activities = List.of(activity);

        BigDecimal result = activityService.addPricesOfActivities(activities);

        assertEquals(BigDecimal.valueOf(20), result);
    }
}