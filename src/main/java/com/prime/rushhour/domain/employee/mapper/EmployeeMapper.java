package com.prime.rushhour.domain.employee.mapper;

import com.prime.rushhour.domain.employee.dto.EmployeeRequest;
import com.prime.rushhour.domain.employee.dto.EmployeeResponse;
import com.prime.rushhour.domain.employee.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "account.role.id", source = "accountRequest.roleId")
    @Mapping(target = "account", source = "accountRequest")
    Employee toEntity(EmployeeRequest employeeRequest);

    @Mapping(target = "accountResponse.role", source = "account.role")
    @Mapping(target = "accountResponse", source = "account")
    EmployeeResponse toDto(Employee employee);

    @Mapping(target = "account.role.id", source = "accountRequest.roleId")
    @Mapping(target = "account", source = "accountRequest")
    void update(@MappingTarget Employee employee, EmployeeRequest employeeRequest);
}