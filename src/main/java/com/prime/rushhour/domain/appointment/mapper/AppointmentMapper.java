package com.prime.rushhour.domain.appointment.mapper;

import com.prime.rushhour.domain.activity.repository.converter.DurationConverter;
import com.prime.rushhour.domain.activity.service.ActivityService;
import com.prime.rushhour.domain.appointment.dto.AppointmentRequest;
import com.prime.rushhour.domain.appointment.dto.AppointmentResponse;
import com.prime.rushhour.domain.appointment.entity.Appointment;
import com.prime.rushhour.domain.client.service.ClientService;
import com.prime.rushhour.domain.employee.service.EmployeeService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {EmployeeService.class, ClientService.class, ActivityService.class, DurationConverter.class})
public interface AppointmentMapper {

    @Mapping(target = "employee", source = "employeeId")
    @Mapping(target = "client", source = "clientId")
    @Mapping(target = "activity", source = "activityId")
    Appointment toEntity(AppointmentRequest appointmentRequest);

    @Mapping(target = "employeeResponse", source = "employee")
    @Mapping(target = "employeeResponse.providerResponse", source = "employee.provider")
    @Mapping(target = "employeeResponse.accountResponse", source = "employee.account")
    @Mapping(target = "clientResponse", source = "client")
    @Mapping(target = "clientResponse.accountResponse", source = "client.account")
    @Mapping(target = "activityResponse", source = "activity")
    @Mapping(target = "activityResponse.providerResponse", source = "activity.provider")
    @Mapping(target = "activityResponse.employeeIds", source = "activity.employees")
    AppointmentResponse toDto(Appointment appointment);

    @Mapping(target = "employee", source = "employeeId")
    @Mapping(target = "client", source = "clientId")
    @Mapping(target = "activity", source = "activityId")
    void update(@MappingTarget Appointment appointment, AppointmentRequest appointmentRequest);
}