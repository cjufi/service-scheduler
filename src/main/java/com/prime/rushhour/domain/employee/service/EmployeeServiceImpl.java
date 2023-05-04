package com.prime.rushhour.domain.employee.service;

import com.prime.rushhour.domain.account.service.AccountService;
import com.prime.rushhour.domain.employee.dto.EmployeeRequest;
import com.prime.rushhour.domain.employee.dto.EmployeeResponse;
import com.prime.rushhour.domain.employee.entity.Employee;
import com.prime.rushhour.domain.employee.mapper.EmployeeMapper;
import com.prime.rushhour.domain.employee.repository.EmployeeRepository;
import com.prime.rushhour.domain.provider.repository.ProviderRepository;
import com.prime.rushhour.domain.role.entity.Role;
import com.prime.rushhour.domain.role.repository.RoleRepository;
import com.prime.rushhour.infrastructure.exceptions.DomainNotCompatibleException;
import com.prime.rushhour.infrastructure.exceptions.DuplicateResourceException;
import com.prime.rushhour.infrastructure.exceptions.EntityNotFoundException;
import com.prime.rushhour.infrastructure.exceptions.RoleNotCompatibleException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    private final AccountService accountService;

    private final ProviderRepository providerRepository;

    private final RoleRepository roleRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, AccountService accountService, ProviderRepository providerRepository, RoleRepository roleRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.accountService = accountService;
        this.providerRepository = providerRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public EmployeeResponse save(EmployeeRequest employeeRequest) {

        if (accountService.checkFullName(employeeRequest.accountRequest().fullName())) {
            throw new DuplicateResourceException("Full Name", employeeRequest.accountRequest().fullName());
        }
        if (accountService.checkEmail(employeeRequest.accountRequest().email())) {
            throw new DuplicateResourceException("Email", employeeRequest.accountRequest().email());
        }
        if (!(providerRepository.findById(employeeRequest.providerId()).get().getBusinessDomain().equals(extractEmailDomain(employeeRequest.accountRequest().email())))) {
            throw new DomainNotCompatibleException("Domain", extractEmailDomain(employeeRequest.accountRequest().email()));
        }
        if (!checkRole(employeeRequest.accountRequest().roleId())) {
            throw new RoleNotCompatibleException(Employee.class.getSimpleName(), employeeRequest.accountRequest().roleId());
        }

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
    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException(Employee.class.getSimpleName(), "id", id);
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeResponse update(Long id, EmployeeRequest employeeRequest) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Employee.class.getSimpleName(), "id", id));

        employeeMapper.update(employee, employeeRequest);
        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    private String extractEmailDomain(String email) {
        String[] parts = email.split("@");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid email address: " + email);
        }
        String[] domainParts = parts[1].split("\\.");
        return domainParts[0];
    }

    private boolean checkRole(Long id) {
        var role = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Role.class.getSimpleName(), "id", id));

        return switch (role.getName()) {
            case "ADMIN", "PROVIDER_ADMIN", "EMPLOYEE" -> true;
            default -> false;
        };
    }
}