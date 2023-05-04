package com.prime.rushhour.domain.employee.mapper;

import com.prime.rushhour.domain.employee.dto.EmployeeRequest;
import com.prime.rushhour.domain.employee.dto.EmployeeResponse;
import com.prime.rushhour.domain.employee.entity.Employee;
import com.prime.rushhour.domain.provider.service.ProviderService;
import com.prime.rushhour.domain.role.service.RoleService;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {RoleService.class, ProviderService.class})
public interface EmployeeMapper {

    @Mapping(target = "provider", source = "providerId")
    @Mapping(target = "account.role", source = "accountRequest.roleId")
    @Mapping(target = "account", source = "accountRequest")
    Employee toEntity(EmployeeRequest employeeRequest);

    @Mapping(target = "providerResponse", source = "provider")
    @Mapping(target = "accountResponse.role", source = "account.role")
    @Mapping(target = "accountResponse", source = "account")
    EmployeeResponse toDto(Employee employee);

    @Mapping(target = "account.role.id", source = "accountRequest.roleId")
    @Mapping(target = "account", source = "accountRequest")
    void update(@MappingTarget Employee employee, EmployeeRequest employeeRequest);
}