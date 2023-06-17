package com.prime.rushhour.domain.appointment.mapper;

import com.prime.rushhour.domain.activity.entity.Activity;
import com.prime.rushhour.domain.activity.repository.converter.DurationConverter;
import com.prime.rushhour.domain.activity.service.ActivityService;
import com.prime.rushhour.domain.appointment.dto.AppointmentRequest;
import com.prime.rushhour.domain.appointment.dto.AppointmentResponse;
import com.prime.rushhour.domain.appointment.entity.Appointment;
import com.prime.rushhour.domain.client.entity.Client;
import com.prime.rushhour.domain.client.service.ClientService;
import com.prime.rushhour.domain.employee.entity.Employee;
import com.prime.rushhour.domain.employee.service.EmployeeService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        uses = {EmployeeService.class, DurationConverter.class})
public abstract class AppointmentMapper {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ActivityService activityService;

    @Mapping(target = "employee", source = "employeeId", qualifiedByName = "toEmployee")
    @Mapping(target = "client", source = "clientId", qualifiedByName = "toClient")
    @Mapping(target = "activity", source = "activityId", qualifiedByName = "toActivity")
    public abstract Appointment toEntity(AppointmentRequest appointmentRequest);

    @Mapping(target = "employee", source = "employee")
    @Mapping(target = "employee.provider", source = "employee.provider")
    @Mapping(target = "employee.account", source = "employee.account")
    @Mapping(target = "client", source = "client")
    @Mapping(target = "client.account", source = "client.account")
    @Mapping(target = "activity", source = "activity")
    @Mapping(target = "activity.provider", source = "activity.provider")
    @Mapping(target = "activity.employeeIds", source = "activity.employees")
    public abstract AppointmentResponse toDto(Appointment appointment);

    @Mapping(target = "employee", source = "employeeId", qualifiedByName = "toEmployee")
    @Mapping(target = "client", source = "clientId", qualifiedByName = "toClient")
    @Mapping(target = "activity", source = "activityId", qualifiedByName = "toActivity")
    public abstract void update(@MappingTarget Appointment appointment, AppointmentRequest appointmentRequest);

    @Named("toEmployee")
    public Employee toEmployee(Long id) {
        return employeeService.idToEmployee(id);
    }

    @Named("toClient")
    public Client toClient(Long id) {
        return clientService.idToClient(id);
    }

    @Named("toActivity")
    public Activity toActivity(Long id) {
        return activityService.idToActivity(id);
    }
}