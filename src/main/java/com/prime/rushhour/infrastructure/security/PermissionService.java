package com.prime.rushhour.infrastructure.security;

import com.prime.rushhour.domain.activity.service.ActivityService;
import com.prime.rushhour.domain.appointment.entity.Appointment;
import com.prime.rushhour.domain.client.service.ClientService;
import com.prime.rushhour.domain.employee.dto.EmployeeRequest;
import com.prime.rushhour.domain.employee.entity.Employee;
import com.prime.rushhour.domain.employee.service.EmployeeService;
import com.prime.rushhour.domain.provider.service.ProviderService;
import com.prime.rushhour.domain.role.service.RoleService;
import com.prime.rushhour.infrastructure.exceptions.UnauthorizedException;
import com.prime.rushhour.infrastructure.service.AuthorizationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    private final EmployeeService employeeService;

    private final ClientService clientService;

    private final ProviderService providerService;

    private final RoleService roleService;

    private final ActivityService activityService;

    private final AuthorizationService authorizationService;

    public PermissionService(EmployeeService employeeService, ClientService clientService, ProviderService providerService, RoleService roleService, ActivityService activityService, AuthorizationService authorizationService) {
        this.employeeService = employeeService;
        this.clientService = clientService;
        this.providerService = providerService;
        this.roleService = roleService;
        this.activityService = activityService;
        this.authorizationService = authorizationService;
    }

    public boolean canProviderAdminAccessEmployee(Long id) {

        authorizationService.canEmployeeAccessEmployee(id);
        var authentication = getAuthentication();
        Long providerId = employeeService.getProviderIdFromAccount(authentication.getAccount().getId());
        Long employeesProviderId = employeeService.getProviderIdFromEmployeeId(id);
        return Objects.equals(employeesProviderId, providerId);
    }

    public boolean canProviderAdminAccessEmployees(List<Long> employeeIds) {
        var authentication = getAuthentication();
        Long providerId = employeeService.getProviderIdFromAccount(authentication.getAccount().getId());
        var provider = providerService.getProviderById(providerId);

        Set<Long> employeeIdSet = provider.getEmployees().stream()
                .map(Employee::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return employeeIdSet.containsAll(employeeIds);
    }

    public boolean canProviderAdminAccessProvider(Long id) {

        authorizationService.canEmployeeAccessProvider(id);
        var authentication = getAuthentication();
        Long providerId = providerService.getProviderIdByAccount(authentication.getAccount().getId());
        return Objects.equals(providerId, id);
    }

    public boolean canProviderAdminCreateEmployee(EmployeeRequest employeeRequest) {

        authorizationService.canEmployeeAccessProvider(employeeRequest.providerId());
        Long roleId = employeeRequest.account().roleId();
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

    public boolean canEmployeeAccessEmployee(Long id) {

        authorizationService.canEmployeeAccessEmployee(id);
        var authentication = getAuthentication();
        Long employeesAccountId = employeeService.getAccountIdFromEmployeeId(id);
        return Objects.equals(employeesAccountId, authentication.getAccount().getId());
    }

    public boolean canEmployeeAccessActivity(List<Long> ids) {
        var authentication = getAuthentication();
        return activityService.isEmployeesActivitySame(ids, authentication.getAccount().getId());
    }

    public boolean canEmployeeAccessAppointment(Long id) {

        authorizationService.canEmployeeAccessAppointment(id);
        var authentication = getAuthentication();
        var employee = employeeService.getEmployeeByAccountId(authentication.getAccount().getId());

        return employee.getAppointments().stream()
                .map(Appointment::getId)
                .filter(Objects::nonNull)
                .anyMatch(appointmentId -> appointmentId.equals(id));
    }

    public boolean canClientAccessClient(Long id) {
        var authentication = getAuthentication();
        Long clientsAccountId = clientService.getAccountIdFromClientId(id);
        if(!(Objects.equals(clientsAccountId, authentication.getAccount().getId()))) {
            throw new UnauthorizedException("You cannot access another clients profile");
        }
        return true;
    }

    public boolean canClientAccessAppointment(Long id) {
        var authentication = getAuthentication();
        var client = clientService.getClientByAccountId(authentication.getAccount().getId());

        client.getAppointments().stream()
                .map(Appointment::getId)
                .filter(Objects::nonNull)
                .filter(appointmentId -> appointmentId.equals(id))
                .findFirst()
                .orElseThrow(() -> new UnauthorizedException("This appointment is not registered for you!"));

        return true;
    }

    private CustomUserDetails getAuthentication() {
        var securityContext = SecurityContextHolder.getContext();
        return (CustomUserDetails) securityContext
                .getAuthentication()
                .getPrincipal();
    }
}