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
import com.prime.rushhour.infrastructure.mapper.price.PriceMapper;
import com.prime.rushhour.infrastructure.mapper.price.PriceMapping;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        uses = {EmployeeService.class, DurationConverter.class, PriceMapper.class})
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

    @Mapping(target = "price", source = "activities", qualifiedBy = PriceMapping.class)
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

    @AfterMapping
    protected void calculateEndDate(AppointmentRequest appointmentRequest, @MappingTarget Appointment appointment) {
        List<Long> activityIds = appointmentRequest.activityIds();
        long totalDurationMinutes = getDurationOfActivities(activityIds);
        Duration totalDuration = Duration.ofMinutes(totalDurationMinutes);

        LocalDateTime newEndDate = appointment.getStartDate().plus(totalDuration);
        appointment.setEndDate(newEndDate);
    }

    private long getDurationOfActivities(List<Long> activityIds) {
        long totalDuration = 0;
        List<Activity> activities = activityService.idsToActivities(activityIds);
        for (Activity activity : activities) {
            totalDuration += activity.getDuration().toMinutes();
        }
        return totalDuration;
    }
}