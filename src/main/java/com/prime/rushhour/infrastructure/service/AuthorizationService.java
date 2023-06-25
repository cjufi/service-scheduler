package com.prime.rushhour.infrastructure.service;

import com.prime.rushhour.domain.activity.service.ActivityService;
import com.prime.rushhour.domain.appointment.entity.Appointment;
import com.prime.rushhour.domain.appointment.service.AppointmentService;
import com.prime.rushhour.domain.employee.service.EmployeeService;
import com.prime.rushhour.infrastructure.exceptions.UnauthorizedException;
import com.prime.rushhour.infrastructure.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final EmployeeService employeeService;

    private final ActivityService activityService;

    private final AppointmentService appointmentService;

    public AuthorizationService(EmployeeService employeeService, ActivityService activityService, AppointmentService appointmentService) {
        this.employeeService = employeeService;
        this.activityService = activityService;
        this.appointmentService = appointmentService;
    }

    public void canEmployeeAccessProvider(Long id) {
        if(!employeeService.isProviderSame(getAuthentication().getAccount().getId(), id)) {
            throw new UnauthorizedException("You are not part of this Provider, so you cannot access this resource!");
        }
    }

    public void canEmployeeAccessEmployee(Long employeeId) {
        if(!employeeService.isEmployeesProviderSame(employeeId, getAuthentication().getAccount().getId())) {
            throw new UnauthorizedException("You cannot access this employee, as he is not part of your Provider");
        }
    }
    public void canEmployeeAccessAppointment(Long appointmentId) {
        Appointment appointment = appointmentService.findById(appointmentId);
        canEmployeeAccessEmployee(appointment.getEmployee().getId());
    }

    private CustomUserDetails getAuthentication() {
        var securityContext = SecurityContextHolder.getContext();
        return (CustomUserDetails) securityContext
                .getAuthentication()
                .getPrincipal();
    }
}
