package com.prime.rushhour.domain.permission.service;

import com.prime.rushhour.domain.client.service.ClientService;
import com.prime.rushhour.domain.employee.dto.EmployeeRequest;
import com.prime.rushhour.domain.employee.service.EmployeeService;
import com.prime.rushhour.domain.provider.service.ProviderService;
import com.prime.rushhour.domain.role.service.RoleService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PermissionServiceImpl implements PermissionService{

    private final EmployeeService employeeService;

    private final ClientService clientService;

    private final ProviderService providerService;

    private final RoleService roleService;

    public PermissionServiceImpl(EmployeeService employeeService, ClientService clientService, ProviderService providerService, RoleService roleService) {
        this.employeeService = employeeService;
        this.clientService = clientService;
        this.providerService = providerService;
        this.roleService = roleService;
    }

    @Override
    public boolean canProviderAdminAccessEmployee(Long id) {
        Long accountId = getAccountIdFromUser();
        Long providerId = employeeService.getProviderIdFromAccount(accountId);
        Long employeesProviderId = employeeService.getProviderIdFromAccount(id);
        return Objects.equals(employeesProviderId, providerId);
    }

    @Override
    public boolean canClientAccessClient(Long id) {
        Long accountId = getAccountIdFromUser();
        Long clientsAccountId = clientService.getAccountIdFromClientId(id);
        return Objects.equals(clientsAccountId, accountId);
    }

    @Override
    public boolean canProviderAdminAccessProvider(Long id) {
        Long accountId = getAccountIdFromUser();
        Long providerId = providerService.getProviderIdByAccount(accountId);
        return Objects.equals(providerId, id);
    }

    @Override
    public boolean canProviderAdminCreateEmployee(EmployeeRequest employeeRequest) {
        Long roleId = employeeRequest.accountRequest().roleId();
        var role = roleService.idToRole(roleId);
        return role.getName().equals("EMPLOYEE");
    }


    private Long getAccountIdFromUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authentication;
        Jwt jwt = jwtAuthentication.getToken();
        return jwt.getClaim("accountId");
    }
}