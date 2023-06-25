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

import java.util.List;

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
    @Mapping(target = "activities", source = "activityIds", qualifiedByName = "toActivities")
    public abstract Appointment toEntity(AppointmentRequest appointmentRequest);


    @Mapping(target = "employee.provider", source = "employee.provider")
    @Mapping(target = "employee.account", source = "employee.account")
    @Mapping(target = "client.account", source = "client.account")
    @Mapping(target = "activities", source = "activities")
    public abstract AppointmentResponse toDto(Appointment appointment);

    @Mapping(target = "employee", source = "employeeId", qualifiedByName = "toEmployee")
    @Mapping(target = "client", source = "clientId", qualifiedByName = "toClient")
    @Mapping(target = "activities", source = "activityIds", qualifiedByName = "toActivities")
    public abstract void update(@MappingTarget Appointment appointment, AppointmentRequest appointmentRequest);

    @Named("toEmployee")
    public Employee toEmployee(Long id) {
        return employeeService.idToEmployee(id);
    }

    @Named("toClient")
    public Client toClient(Long id) {
        return clientService.idToClient(id);
    }

    @Named("toActivities")
    public List<Activity> toActivity(List<Long> ids) {
        return activityService.idsToActivities(ids);
    }

    @Named("toEmployeeIds")
    public List<Long> toEmployeeIds(List<Employee> employees) {
        return employeeService.EmployeesToIds(employees);
    }
}