package com.prime.rushhour.domain.employee.mapper;

import com.prime.rushhour.domain.employee.dto.EmployeeRequest;
import com.prime.rushhour.domain.employee.dto.EmployeeResponse;
import com.prime.rushhour.domain.employee.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEntity(EmployeeRequest employeeRequest);

    EmployeeResponse toDto(Employee employee);

    void update(@MappingTarget Employee employee, EmployeeRequest employeeRequest);
}