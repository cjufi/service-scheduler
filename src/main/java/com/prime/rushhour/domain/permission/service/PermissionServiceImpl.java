package com.prime.rushhour.domain.permission.service;

import com.prime.rushhour.domain.employee.service.EmployeeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PermissionServiceImpl implements PermissionService{

    private final EmployeeService employeeService;

    public PermissionServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public boolean canProviderAdminAccessEmployee(Long id) {
        var authentication = getAuthentication();
        JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authentication;
        Jwt jwt = jwtAuthentication.getToken();
        Long accountId = jwt.getClaim("accountId");
        Long providerId = employeeService.getProviderIdFromAccount(accountId);
        return Objects.equals(id, providerId);
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}