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
import com.prime.rushhour.domain.token.service.TokenService;
import com.prime.rushhour.infrastructure.exceptions.DomainNotCompatibleException;
import com.prime.rushhour.infrastructure.exceptions.EntityNotFoundException;
import com.prime.rushhour.infrastructure.exceptions.PermissionDeniedException;
import com.prime.rushhour.infrastructure.exceptions.RoleNotCompatibleException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    private final AccountService accountService;

    private final ProviderService providerService;

    private final RoleService roleService;

    private final TokenService tokenService;

    private final AuthenticationManager manager;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, AccountService accountService, ProviderService providerService, RoleService roleService, TokenService tokenService, AuthenticationManager manager) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.accountService = accountService;
        this.providerService = providerService;
        this.roleService = roleService;
        this.tokenService = tokenService;
        this.manager = manager;
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = roleService.getNameById(employeeUpdateRequest.accountUpdateRequest().roleId());

        if("[SCOPE_ADMIN]".equals(authentication.getAuthorities().toString())) {
            if(role.equals("ADMIN")) {
                throw new PermissionDeniedException(role);
            }
        }

        if("[SCOPE_PROVIDER_ADMIN]".equals(authentication.getAuthorities().toString())) {
            if(role.equals("ADMIN")) {
                throw new PermissionDeniedException(role);
            }
        }

        if (!checkRole(employeeUpdateRequest.accountUpdateRequest().roleId())) {
            throw new RoleNotCompatibleException(Employee.class.getSimpleName(), employeeUpdateRequest.accountUpdateRequest().roleId());
        }

        employeeMapper.update(employee, employeeUpdateRequest);
        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    @Override
    public void deleteByProviderId(Long id) {
        employeeRepository.deleteEmployeesByProviderId(id);
    }

    @Override
    public String login(String email, String password) {
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        return tokenService.generateToken(authentication);
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
        if (!checkRole(employeeRequest.accountRequest().roleId())) {
            throw new RoleNotCompatibleException(Employee.class.getSimpleName(), employeeRequest.accountRequest().roleId());
        }
    }

    private boolean checkRole(Long id) {
        var role = roleService.getById(id);
        var roleType = RoleType.valueOf(role.name().toUpperCase());
        return roleType == RoleType.ADMIN || roleType == RoleType.PROVIDER_ADMIN || roleType == RoleType.EMPLOYEE;
    }
}