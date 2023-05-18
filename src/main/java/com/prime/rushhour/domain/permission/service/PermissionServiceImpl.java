package com.prime.rushhour.domain.permission.service;

import com.prime.rushhour.domain.client.service.ClientService;
import com.prime.rushhour.domain.employee.dto.EmployeeRequest;
import com.prime.rushhour.domain.employee.service.EmployeeService;
import com.prime.rushhour.domain.provider.service.ProviderService;
import com.prime.rushhour.domain.role.service.RoleService;
import com.prime.rushhour.infrastructure.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
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
        var authentication = getAuthentication();
        Long providerId = employeeService.getProviderIdFromAccount(authentication.getAccount().getId());
        Long employeesProviderId = employeeService.getProviderIdFromAccount(id);
        return Objects.equals(employeesProviderId, providerId);
    }

    @Override
    public boolean canClientAccessClient(Long id) {
        var authentication = getAuthentication();
        Long clientsAccountId = clientService.getAccountIdFromClientId(id);
        return Objects.equals(clientsAccountId, authentication.getAccount().getId());
    }

    @Override
    public boolean canEmployeeAccessEmployee(Long id) {
        var authentication = getAuthentication();
        Long employeesAccountId = employeeService.getAccountIdFromEmployeeId(id);
        return Objects.equals(employeesAccountId, authentication.getAccount().getId());
    }

    @Override
    public boolean canProviderAdminAccessProvider(Long id) {
        var authentication = getAuthentication();
        Long providerId = providerService.getProviderIdByAccount(authentication.getAccount().getId());
        return Objects.equals(providerId, id);
    }

    @Override
    public boolean canProviderAdminCreateEmployee(EmployeeRequest employeeRequest) {
        Long roleId = employeeRequest.accountRequest().roleId();
        var role = roleService.idToRole(roleId);
        var authentication = getAuthentication();
        Long providerId = providerService.getProviderIdByAccount(authentication.getAccount().getId());
        return role.getName().equals("EMPLOYEE") && (employeeRequest.providerId().equals(providerId));
    }


    private CustomUserDetails getAuthentication() {
        var securityContext = SecurityContextHolder.getContext();
        return (CustomUserDetails) securityContext
                .getAuthentication()
                .getPrincipal();
    }
}