package com.prime.rushhour.infrastructure.security;

import com.prime.rushhour.domain.activity.service.ActivityService;
import com.prime.rushhour.domain.client.service.ClientService;
import com.prime.rushhour.domain.employee.dto.EmployeeRequest;
import com.prime.rushhour.domain.employee.service.EmployeeService;
import com.prime.rushhour.domain.provider.service.ProviderService;
import com.prime.rushhour.domain.role.service.RoleService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PermissionService {

    private final EmployeeService employeeService;

    private final ClientService clientService;

    private final ProviderService providerService;

    private final RoleService roleService;

    private final ActivityService activityService;

    public PermissionService(EmployeeService employeeService, ClientService clientService, ProviderService providerService, RoleService roleService, ActivityService activityService) {
        this.employeeService = employeeService;
        this.clientService = clientService;
        this.providerService = providerService;
        this.roleService = roleService;
        this.activityService = activityService;
    }

    public boolean canProviderAdminAccessEmployee(Long id) {
        var authentication = getAuthentication();
        Long providerId = employeeService.getProviderIdFromAccount(authentication.getAccount().getId());
        Long employeesProviderId = employeeService.getProviderIdFromEmployeeId(id);
        return Objects.equals(employeesProviderId, providerId);
    }

    public boolean canProviderAdminAccessEmployees(List<Long> employeeIds) {
        var authentication = getAuthentication();
        Long providerId = employeeService.getProviderIdFromAccount(authentication.getAccount().getId());
        for(Long employeeId: employeeIds) {
            Long employeesProviderId = employeeService.getProviderIdFromEmployeeId(employeeId);
            if(!Objects.equals(employeesProviderId,providerId)) {
                return false;
            }
        }
        return true;
    }

    public boolean canClientAccessClient(Long id) {
        var authentication = getAuthentication();
        Long clientsAccountId = clientService.getAccountIdFromClientId(id);
        return Objects.equals(clientsAccountId, authentication.getAccount().getId());
    }

    public boolean canEmployeeAccessEmployee(Long id) {
        var authentication = getAuthentication();
        Long employeesAccountId = employeeService.getAccountIdFromEmployeeId(id);
        return Objects.equals(employeesAccountId, authentication.getAccount().getId());
    }

    public boolean canProviderAdminAccessProvider(Long id) {
        var authentication = getAuthentication();
        Long providerId = providerService.getProviderIdByAccount(authentication.getAccount().getId());
        return Objects.equals(providerId, id);
    }

    public boolean canProviderAdminCreateEmployee(EmployeeRequest employeeRequest) {
        Long roleId = employeeRequest.accountRequest().roleId();
        var role = roleService.idToRole(roleId);
        var authentication = getAuthentication();
        Long providerId = providerService.getProviderIdByAccount(authentication.getAccount().getId());
        return role.getName().equals("EMPLOYEE") && (employeeRequest.providerId().equals(providerId));
    }

    public boolean canProviderAdminAccessActivity(Long id) {
        var authentication = getAuthentication();
        var accountId = authentication.getAccount().getId();
        var providerId = activityService.getProviderIdFromActivityId(id);
        return activityService.getProviderIdFromAccountId(accountId).equals(providerId);
    }


    private CustomUserDetails getAuthentication() {
        var securityContext = SecurityContextHolder.getContext();
        return (CustomUserDetails) securityContext
                .getAuthentication()
                .getPrincipal();
    }
}