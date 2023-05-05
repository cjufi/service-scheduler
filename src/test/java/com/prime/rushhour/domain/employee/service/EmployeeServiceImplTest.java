package com.prime.rushhour.domain.employee.service;

import com.prime.rushhour.domain.account.dto.AccountRequest;
import com.prime.rushhour.domain.account.dto.AccountResponse;
import com.prime.rushhour.domain.account.entity.Account;
import com.prime.rushhour.domain.account.service.AccountService;
import com.prime.rushhour.domain.employee.dto.EmployeeRequest;
import com.prime.rushhour.domain.employee.dto.EmployeeResponse;
import com.prime.rushhour.domain.employee.entity.Employee;
import com.prime.rushhour.domain.employee.mapper.EmployeeMapper;
import com.prime.rushhour.domain.employee.repository.EmployeeRepository;
import com.prime.rushhour.domain.provider.service.ProviderService;
import com.prime.rushhour.domain.role.dto.RoleDto;
import com.prime.rushhour.domain.role.entity.Role;
import com.prime.rushhour.domain.role.service.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private AccountService accountService;

    @Mock
    private ProviderService providerService;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void save() {

        EmployeeRequest employeeRequest = new EmployeeRequest("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                new AccountRequest("ftasic39@gmail.com", "FilipTasic", "Password12#", 2L), 1L);

        Employee employee = new Employee("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                new Account("ftasic39@gmail.com", "FilipTasic", "Password12#", new Role("PROVIDER_ADMIN")), null);

        EmployeeResponse employeeResponse = new EmployeeResponse("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                new AccountResponse("ftasic39@gmail.com", "FilipTasic", new RoleDto("PROVIDER_ADMIN")), null);

        when(employeeMapper.toEntity(employeeRequest)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.toDto(employee)).thenReturn(employeeResponse);

        EmployeeResponse result = employeeService.save(employeeRequest);


        assertEquals(employeeResponse, result);
    }

    @Test
    void getById() {

        Employee employee = new Employee("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                new Account("ftasic39@gmail.com", "FilipTasic", "Password12#", new Role("PROVIDER_ADMIN")), null);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        EmployeeResponse employeeResponse = new EmployeeResponse("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                new AccountResponse("ftasic39@gmail.com", "FilipTasic", new RoleDto("PROVIDER_ADMIN")), null);

        when(employeeMapper.toDto(employee)).thenReturn(employeeResponse);

        employeeResponse = employeeService.getById(1L);

        assertEquals(employee.getPhone(), employeeResponse.phone());
    }

    @Test
    void getAll() {

        List<Employee> employees = Arrays.asList(
                new Employee("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                        new Account("ftasic39@gmail.com", "FilipTasic", "Password12#", new Role("PROVIDER_ADMIN")), null),
                new Employee("Senior", "+3816981522342", 20.00, LocalDate.of(2018, 8, 30),
                        new Account("svetlana@gmail.com", "SvetlanaStojanovic", "Password12#", new Role("PROVIDER_ADMIN")), null)
        );

        Page<Employee> page = new PageImpl<>(employees);

        when(employeeRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(employeeMapper.toDto(any(Employee.class))).thenReturn(new EmployeeResponse("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                new AccountResponse("ftasic39@gmail.com", "FilipTasic", new RoleDto("PROVIDER_ADMIN")), null));

        Page<EmployeeResponse> result = employeeService.getAll(PageRequest.of(0, 10));

        assertEquals(2, result.getSize());
        assertEquals("Junior", result.getContent().get(0).title());
    }

    @Test
    void delete() {

        Long employeeId = 1L;

        when(employeeRepository.existsById(employeeId)).thenReturn(true);

        employeeService.delete(employeeId);

        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

    @Test
    void update() {

        EmployeeRequest employeeRequest = new EmployeeRequest("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                new AccountRequest("ftasic39@gmail.com", "FilipTasic", "Password12#", 2L), 1L);

        Employee employee = new Employee("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                new Account("ftasic39@gmail.com", "FilipTasic", "Password12#", new Role("PROVIDER_ADMIN")), null);

        EmployeeResponse employeeResponse = new EmployeeResponse("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                new AccountResponse("ftasic39@gmail.com", "FilipTasic", new RoleDto("PROVIDER_ADMIN")), null);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.toDto(employee)).thenReturn(employeeResponse);

        var result = employeeService.update(1L, employeeRequest);

        assertEquals(result, employeeResponse);

        verify(employeeRepository).findById(1L);
        verify(employeeMapper).update(employee, employeeRequest);
        verify(employeeRepository).save(employee);
        verify(employeeMapper).toDto(employee);
    }
}