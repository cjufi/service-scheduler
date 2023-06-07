package com.prime.rushhour.domain.employee.service;

import com.prime.rushhour.domain.account.service.AccountService;
import com.prime.rushhour.domain.employee.dto.EmployeeRequest;
import com.prime.rushhour.domain.employee.dto.EmployeeResponse;
import com.prime.rushhour.domain.employee.dto.EmployeeUpdateRequest;
import com.prime.rushhour.domain.employee.entity.Employee;
import com.prime.rushhour.domain.employee.mapper.EmployeeMapper;
import com.prime.rushhour.domain.employee.repository.EmployeeRepository;
import com.prime.rushhour.domain.provider.service.ProviderService;
import com.prime.rushhour.domain.role.entity.RoleType;
import com.prime.rushhour.domain.role.service.RoleService;
import com.prime.rushhour.infrastructure.exceptions.DomainNotCompatibleException;
import com.prime.rushhour.infrastructure.exceptions.EntityNotFoundException;
import com.prime.rushhour.infrastructure.exceptions.RoleNotCompatibleException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    private final AccountService accountService;

    private final ProviderService providerService;

    private final RoleService roleService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, AccountService accountService, ProviderService providerService, RoleService roleService) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.accountService = accountService;
        this.providerService = providerService;
        this.roleService = roleService;
    }

    @Override
    public EmployeeResponse save(EmployeeRequest employeeRequest) {

        employeeValidation(employeeRequest);

        var employee = employeeMapper.toEntity(employeeRequest);
        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    @Override
    public EmployeeResponse getById(Long id) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Employee.class.getSimpleName(), "id", id));
        return employeeMapper.toDto(employee);
    }

    @Override
    public Page<EmployeeResponse> getAll(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(employeeMapper::toDto);
    }

    @Override
    public Page<EmployeeResponse> getAllFromSameProvider(Pageable pageable, Long id) {
        Long providerId = getProviderIdFromAccount(id);
        return employeeRepository.findEmployeesByProviderId(pageable, providerId).map(employeeMapper::toDto);
    }

    @Override
    public Long getProviderIdFromEmployeeId(Long id) {
        return employeeRepository.findProviderIdByEmployeeId(id);
    }

    @Override
    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException(Employee.class.getSimpleName(), "id", id);
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeResponse update(Long id, EmployeeUpdateRequest employeeUpdateRequest) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Employee.class.getSimpleName(), "id", id));

        if (checkRole(employeeUpdateRequest.accountUpdateRequest().roleId())) {
            throw new RoleNotCompatibleException(Employee.class.getSimpleName(), employeeUpdateRequest.accountUpdateRequest().roleId());
        }

        employeeMapper.update(employee, employeeUpdateRequest);
        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    @Override
    public Long getProviderIdFromAccount(Long accountId) {
        return employeeRepository.findProviderIdByAccountId(accountId);
    }

    @Override
    public Long getAccountIdFromEmployeeId(Long id) {
        return employeeRepository.findAccountIdByEmployeeId(id);
    }

    @Override
    public List<Employee> idsToEmployees(List<Long> employeeIds) {
        return employeeRepository.findByIdIn(employeeIds);
    }

    @Override
    public List<Long> EmployeesToIds(List<Employee> employees) {

        List<Long> employeeIds = new ArrayList<>();

        for(Employee employee: employees) {
            if (employee.getId() != null) {
                employeeIds.add(employee.getId());
            }
        }
        return employeeIds;
    }

    @Override
    public Employee idToEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Employee.class.getSimpleName(), "id", id));
    }

    @Override
    public Long getEmployeesProviderIdByAccount(Long id) {
        return employeeRepository.findEmployeesProviderIdByAccountId(id);
    }

    @Override
    public Employee getEmployeeByAccountId(Long id) {
        return employeeRepository.findByAccountId(id);
    }

    private String extractEmailDomain(String email) {
        String[] parts = email.split("@");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid email address: " + email);
        }
        return parts[1];
    }

    private void employeeValidation(EmployeeRequest employeeRequest) {

        accountService.validateAccount(employeeRequest.accountRequest());

        var emailDomain = extractEmailDomain(employeeRequest.accountRequest().email());

        if (!(providerService.getProviderById(employeeRequest.providerId()).getBusinessDomain().equals(emailDomain))) {
            throw new DomainNotCompatibleException("Domain", emailDomain);
        }
        if (checkRole(employeeRequest.accountRequest().roleId())) {
            throw new RoleNotCompatibleException(Employee.class.getSimpleName(), employeeRequest.accountRequest().roleId());
        }
    }

    private boolean checkRole(Long id) {
        var role = roleService.getById(id);
        var roleType = RoleType.valueOf(role.name().toUpperCase());
        return roleType != RoleType.ADMIN && roleType != RoleType.PROVIDER_ADMIN && roleType != RoleType.EMPLOYEE;
    }
}