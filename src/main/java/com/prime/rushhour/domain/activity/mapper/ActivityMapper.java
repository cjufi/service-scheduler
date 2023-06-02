package com.prime.rushhour.domain.activity.mapper;

import com.prime.rushhour.domain.activity.dto.ActivityRequest;
import com.prime.rushhour.domain.activity.dto.ActivityResponse;
import com.prime.rushhour.domain.activity.entity.Activity;
import com.prime.rushhour.domain.activity.repository.converter.DurationConverter;
import com.prime.rushhour.domain.employee.service.EmployeeService;
import com.prime.rushhour.domain.provider.service.ProviderService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ProviderService.class, EmployeeService.class, DurationConverter.class})
public interface ActivityMapper {

    @Mapping(target = "provider", source = "providerId")
    @Mapping(target = "employees", source = "employeeIds")
    Activity toEntity(ActivityRequest activityRequest);

    @Mapping(target = "providerResponse", source = "provider")
    @Mapping(target = "employeeIds", source = "employees")
    ActivityResponse toDto(Activity activity);

    @Mapping(target = "provider", ignore = true)
    @Mapping(target = "employees", source = "employeeIds")
    void update(@MappingTarget Activity activity, ActivityRequest activityRequest);
}