package com.prime.rushhour.infrastructure.service;

import com.prime.rushhour.domain.employee.service.EmployeeService;
import com.prime.rushhour.domain.provider.service.ProviderService;
import com.prime.rushhour.infrastructure.exceptions.UnauthorizedException;
import com.prime.rushhour.infrastructure.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final EmployeeService employeeService;

    public AuthorizationService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void canEmployeeAccessProvider(Long id) {
        if(!employeeService.isProviderSame(getAuthentication().getAccount().getId(), id)) {
            throw new UnauthorizedException("You are not part of this Provider, so you cannot access this resource!");
        }
    }

    public void canEmployeeAccessEmployee(Long id) {
        if(!employeeService.isEmployeesProviderSame(id, getAuthentication().getAccount().getId())) {
            throw new UnauthorizedException("You cannot access this employee, as he is not part of your Provider");
        }
    }

    private CustomUserDetails getAuthentication() {
        var securityContext = SecurityContextHolder.getContext();
        return (CustomUserDetails) securityContext
                .getAuthentication()
                .getPrincipal();
    }
}
