package com.prime.rushhour.domain.appointment.service;

import com.prime.rushhour.domain.account.dto.AccountResponse;
import com.prime.rushhour.domain.account.entity.Account;
import com.prime.rushhour.domain.activity.dto.ActivityResponse;
import com.prime.rushhour.domain.activity.entity.Activity;
import com.prime.rushhour.domain.activity.service.ActivityService;
import com.prime.rushhour.domain.appointment.dto.AppointmentRequest;
import com.prime.rushhour.domain.appointment.dto.AppointmentResponse;
import com.prime.rushhour.domain.appointment.entity.Appointment;
import com.prime.rushhour.domain.appointment.mapper.AppointmentMapper;
import com.prime.rushhour.domain.appointment.repository.AppointmentRepository;
import com.prime.rushhour.domain.client.dto.ClientResponse;
import com.prime.rushhour.domain.client.entity.Client;
import com.prime.rushhour.domain.employee.dto.EmployeeResponse;
import com.prime.rushhour.domain.employee.entity.Employee;
import com.prime.rushhour.domain.provider.dto.ProviderResponse;
import com.prime.rushhour.domain.provider.entity.Provider;
import com.prime.rushhour.domain.role.dto.RoleDto;
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
import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @Mock
    private ActivityService activityService;

    private AppointmentRequest appointmentRequest;

    private Appointment appointment;

    private AppointmentResponse appointmentResponse;

    @BeforeEach
    void setUp() {

        appointmentRequest = new AppointmentRequest(
                LocalDateTime.of(LocalDate.of(2023, 12, 2),
                        LocalTime.of(8,0,0)),
                        1L, 1L, List.of(1L,2L));

        appointment = new Appointment(
                LocalDateTime.of(LocalDate.of(2023, 12, 2),
                        LocalTime.of(8,0,0)),
                LocalDateTime.of(LocalDate.of(2023, 12, 2),
                        LocalTime.of(10,0,0)),
                new Employee("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                new Account("ftasic39@gmail.com", "FilipTasic", "Password12#", new Role("PROVIDER_ADMIN")),
                new Provider("Filip Massage", "https://filip.com", "gmail.com", "+3816555333", LocalTime.of(8, 0), LocalTime.of(15,0), Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY))),
                new Client("0621296070", "Ledinaja 2", new Account("filip@gmail.com", "FilipTasic", "Password12#", new Role("CLIENT"))),
                List.of(new Activity(
                        1L, "Code Review", BigDecimal.valueOf(20), Duration.ofMinutes(240),
                        new Provider("Filip Massage", "https://filip.com", "ft12", "+3816555333",
                                LocalTime.of(8, 0, 0), LocalTime.of(17, 0, 0),
                                Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY)),
                        List.of(
                                new Employee("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                                        new Account("ftasic39@gmail.com", "FilipTasic", "Password12#", new Role("PROVIDER_ADMIN")),
                                        new Provider("Filip Massage", "https://filip.com", "gmail.com", "+3816555333",
                                                LocalTime.of(8, 0), LocalTime.of(15, 0), Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY)))
                        )),
                        new Activity(
                                2L, "Code Refactor", BigDecimal.valueOf(20), Duration.ofMinutes(240),
                                new Provider("Filip Massage", "https://filip.com", "ft12", "+3816555333",
                                        LocalTime.of(8, 0, 0), LocalTime.of(17, 0, 0),
                                        Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY)),
                                List.of(
                                        new Employee("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                                                new Account("ftasic39@gmail.com", "FilipTasic", "Password12#", new Role("PROVIDER_ADMIN")),
                                                new Provider("Filip Massage", "https://filip.com", "gmail.com", "+3816555333",
                                                        LocalTime.of(8, 0), LocalTime.of(15, 0), Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY)))
                                )))
        );

        appointmentResponse = new AppointmentResponse(
                LocalDateTime.of(LocalDate.of(2023, 12, 2),
                        LocalTime.of(8,0,0)),
                LocalDateTime.of(LocalDate.of(2023, 12, 2),
                        LocalTime.of(10,0,0)),
                BigDecimal.valueOf(20),
                new EmployeeResponse("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                new AccountResponse("ftasic39@gmail.com", "FilipTasic", new RoleDto("PROVIDER_ADMIN")),
                new ProviderResponse("Filip Massage", "https://filip.com", "gmail.com", "+3816555333", LocalTime.of(8, 0), LocalTime.of(15,0), Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY))),
                new ClientResponse("0621296070", "Ledinaja 2", new AccountResponse("filip@gmail.com", "FilipTasic", new RoleDto("CLIENT"))),
                List.of(
                        new ActivityResponse(
                                "Code Review", BigDecimal.valueOf(20),
                                240L, new ProviderResponse("Prime Software", "https://filip.com",
                                "ft12", "+3816555333",
                                LocalTime.of(8, 0, 0), LocalTime.of(17, 0, 0),
                                Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY)), List.of(1L, 2L)),
                        new ActivityResponse(
                                "Code Refactor", BigDecimal.valueOf(20),
                                240L, new ProviderResponse("Prime Software", "https://filip.com",
                                "ft12", "+3816555333",
                                LocalTime.of(8, 0, 0), LocalTime.of(17, 0, 0),
                                Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY)), List.of(1L, 2L))
                    )
                );
    }

    @Test
    void save() {
        when(appointmentMapper.toEntity(appointmentRequest)).thenReturn(appointment);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        when(appointmentMapper.toDto(appointment)).thenReturn(appointmentResponse);

        AppointmentResponse result = appointmentService.save(appointmentRequest);

        assertEquals(appointmentResponse, result);
    }

    @Test
    void getById() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(appointmentMapper.toDto(appointment)).thenReturn(appointmentResponse);

        var result = appointmentService.getById(1L);

        assertEquals(result.employee().phone(), "+381698154562");
    }

    @Test
    void getAll() {
        List<Appointment> appointments = List.of(appointment);

        Page<Appointment> page = new PageImpl<>(appointments);

        when(appointmentRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(appointmentMapper.toDto(any(Appointment.class))).thenReturn(appointmentResponse);

        Page<AppointmentResponse> result = appointmentService.getAll(PageRequest.of(0, 10));

        assertEquals(1, result.getSize());
    }

    @Test
    void delete() {
        when(appointmentRepository.existsById(1L)).thenReturn(true);
        appointmentService.delete(1L);
        verify(appointmentRepository, times(1)).deleteById(1L);
    }

    @Test
    void update() {
        when(appointmentRepository.findById(1L)).
                thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(appointment)).
                thenReturn(appointment);
        when(appointmentMapper.toDto(appointment)).thenReturn(appointmentResponse);

        var result = appointmentService.update(1L, appointmentRequest);

        assertEquals(result, appointmentResponse);

        verify(appointmentMapper).update(appointment, appointmentRequest);
    }
}