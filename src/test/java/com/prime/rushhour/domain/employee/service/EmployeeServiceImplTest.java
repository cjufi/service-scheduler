package com.prime.rushhour.domain.employee.service;

import com.prime.rushhour.domain.account.dto.AccountRequest;
import com.prime.rushhour.domain.account.dto.AccountResponse;
import com.prime.rushhour.domain.account.dto.AccountUpdateRequest;
import com.prime.rushhour.domain.account.entity.Account;
import com.prime.rushhour.domain.account.service.AccountService;
import com.prime.rushhour.domain.employee.dto.EmployeeRequest;
import com.prime.rushhour.domain.employee.dto.EmployeeResponse;
import com.prime.rushhour.domain.employee.dto.EmployeeUpdateRequest;
import com.prime.rushhour.domain.employee.entity.Employee;
import com.prime.rushhour.domain.employee.mapper.EmployeeMapper;
import com.prime.rushhour.domain.employee.repository.EmployeeRepository;
import com.prime.rushhour.domain.provider.dto.ProviderResponse;
import com.prime.rushhour.domain.provider.entity.Provider;
import com.prime.rushhour.domain.provider.service.ProviderService;
import com.prime.rushhour.domain.role.dto.RoleDto;
import com.prime.rushhour.domain.role.entity.Role;
import com.prime.rushhour.domain.role.service.RoleService;
import com.prime.rushhour.infrastructure.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private ProviderService providerService;

    @Mock
    private RoleService roleService;

    @Mock
    private AccountService accountService;

    @Test
    void save() {

        EmployeeRequest employeeRequest = new EmployeeRequest("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                new AccountRequest("ftasic39@gmail.com", "FilipTasic", "Password12#", 2L), 1L);

        Employee employee = new Employee("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                new Account("ftasic39@gmail.com", "FilipTasic", "Password12#", new Role("PROVIDER_ADMIN")),
                new Provider("Filip Massage", "https://filip.com", "gmail.com", "+3816555333", LocalTime.of(8, 0), LocalTime.of(15,0), Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY)));

        EmployeeResponse employeeResponse = new EmployeeResponse("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                new AccountResponse("ftasic39@gmail.com", "FilipTasic", new RoleDto("PROVIDER_ADMIN")),
                new ProviderResponse("Filip Massage", "https://filip.com", "gmail.com", "+3816555333", LocalTime.of(8, 0), LocalTime.of(15,0), Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY)));

        when(providerService.getProviderById(1L)).thenReturn(employee.getProvider());
        when(roleService.getById(2L)).thenReturn(new RoleDto("PROVIDER_ADMIN"));
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
                new Employee("Senior", "+3816981522342", 20.0, LocalDate.of(2018, 8, 30),
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

        EmployeeUpdateRequest employeeUpdateRequest = new EmployeeUpdateRequest("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                new AccountUpdateRequest( "FilipTasic", "Password12#", 2L), 1L);

        Employee employee = new Employee("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                new Account("ftasic39@gmail.com", "FilipTasic", "Password12#", new Role("PROVIDER_ADMIN")),
                new Provider("Filip Massage", "https://filip.com", "gmail.com", "+3816555333", LocalTime.of(8,0), LocalTime.of(15,0), Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY)));

        EmployeeResponse employeeResponse = new EmployeeResponse("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                new AccountResponse("ftasic39@gmail.com", "FilipTasic", new RoleDto("PROVIDER_ADMIN")),
                new ProviderResponse("Filip Massage", "https://filip.com", "gmail.com", "+3816555333", LocalTime.of(8,0), LocalTime.of(15,0), Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY)));

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(roleService.getById(2L)).thenReturn(new RoleDto("PROVIDER_ADMIN"));
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.toDto(employee)).thenReturn(employeeResponse);

        var result = employeeService.update(1L, employeeUpdateRequest);

        assertEquals(result, employeeResponse);

        verify(employeeRepository).findById(1L);
        verify(employeeMapper).update(employee, employeeUpdateRequest);
        verify(employeeRepository).save(employee);
        verify(employeeMapper).toDto(employee);
    }

    @Test
    void getProviderIdFromAccount() {
        Long accountId = 1L;
        Long providerId = 1L;

        when(employeeRepository.findProviderIdByAccountId(accountId)).thenReturn(providerId);
        Long result = employeeService.getProviderIdFromAccount(accountId);
        assertEquals(providerId, result);
        verify(employeeRepository).findProviderIdByAccountId(accountId);
    }

    @Test
    void getAccountIdFromEmployeeId() {
        Long employeeId = 1L;
        Long accountId = 1L;

        when(employeeRepository.findAccountIdByEmployeeId(employeeId)).thenReturn(accountId);
        Long result = employeeService.getAccountIdFromEmployeeId(employeeId);
        assertEquals(accountId, result);
        verify(employeeRepository).findAccountIdByEmployeeId(employeeId);
    }

    @Test
    void idsToEmployees() {
        List<Long> employeeIds = List.of(1L, 2L);
        List<Employee> expectedEmployees = List.of(new Employee(), new Employee());

        when(employeeRepository.findByIdIn(employeeIds)).thenReturn(expectedEmployees);
        List<Employee> result = employeeService.idsToEmployees(employeeIds);
        assertEquals(expectedEmployees, result);
        verify(employeeRepository).findByIdIn(employeeIds);
    }

    @Test
    void idsToEmployees_WhenEmployeeIdsDoNotExist() {
        List<Long> employeeIds = List.of();

        when(employeeRepository.findByIdIn(employeeIds)).thenReturn(List.of());
        List<Employee> result = employeeService.idsToEmployees(employeeIds);
        assertTrue(result.isEmpty());
        verify(employeeRepository).findByIdIn(employeeIds);
    }

    @Test
    void employeesToIds_WhenEmployeesExist() {
        List<Employee> employees = List.of(
                new Employee("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                        new Account("ftasic39@gmail.com", "FilipTasic", "Password12#", new Role("PROVIDER_ADMIN")), null),
                new Employee("Senior", "+3816981522342", 20.0, LocalDate.of(2018, 8, 30),
                        new Account("svetlana@gmail.com", "SvetlanaStojanovic", "Password12#", new Role("PROVIDER_ADMIN")), null)
        );
        employees.get(0).setId(1L);
        employees.get(1).setId(2L);
        List<Long> expectedIds = List.of(1L, 2L);

        List<Long> result = employeeService.EmployeesToIds(employees);

        assertEquals(expectedIds, result);
    }

    @Test
    void employeesToIds_WhenEmployeesDoNotExist() {
        List<Employee> employees = List.of(
                new Employee("Junior", "+381698154562", 12.67, LocalDate.of(2020, 8, 30),
                        new Account("ftasic39@gmail.com", "FilipTasic", "Password12#", new Role("PROVIDER_ADMIN")), null),
                new Employee("Senior", "+3816981522342", 20.0, LocalDate.of(2018, 8, 30),
                        new Account("svetlana@gmail.com", "SvetlanaStojanovic", "Password12#", new Role("PROVIDER_ADMIN")), null)
        );

        List<Long> result = employeeService.EmployeesToIds(employees);

        assertTrue(result.isEmpty());
    }

    @Test
    void idToEmployee_WhenEmployeeExists() {
        Long employeeId = 1L;
        Employee employee = new Employee();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        Employee result = employeeService.idToEmployee(employeeId);

        assertNotNull(result);
        assertEquals(employee, result);

        verify(employeeRepository).findById(employeeId);
    }

    @Test
    void idToEmployee_WhenEmployeeDoesNotExist_ThrowsException() {
        Long employeeId = 1L;

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> employeeService.idToEmployee(employeeId));

        verify(employeeRepository).findById(employeeId);
    }
}