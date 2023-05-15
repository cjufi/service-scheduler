package com.prime.rushhour.domain.employee.mapper;

import com.prime.rushhour.domain.employee.dto.EmployeeRequest;
import com.prime.rushhour.domain.employee.dto.EmployeeResponse;
import com.prime.rushhour.domain.employee.dto.EmployeeUpdateRequest;
import com.prime.rushhour.domain.employee.entity.Employee;
import com.prime.rushhour.domain.provider.service.ProviderService;
import com.prime.rushhour.domain.role.service.RoleService;
import com.prime.rushhour.infrastructure.mapper.EncodedMapping;
import com.prime.rushhour.infrastructure.mapper.PasswordEncoderMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {RoleService.class, ProviderService.class, PasswordEncoderMapper.class})
public interface EmployeeMapper {

    @Mapping(target = "provider", source = "providerId")
    @Mapping(target = "account.role", source = "accountRequest.roleId")
    @Mapping(target = "account", source = "accountRequest")
    @Mapping(target = "account.password", qualifiedBy = EncodedMapping.class)
    Employee toEntity(EmployeeRequest employeeRequest);

    @Mapping(target = "providerResponse", source = "provider")
    @Mapping(target = "accountResponse.role", source = "account.role")
    @Mapping(target = "accountResponse", source = "account")
    EmployeeResponse toDto(Employee employee);

    @Mapping(target = "account.role", source = "accountUpdateRequest.roleId")
    @Mapping(target = "account", source = "accountUpdateRequest")
    @Mapping(target = "account.password", qualifiedBy = EncodedMapping.class)
    void update(@MappingTarget Employee employee, EmployeeUpdateRequest employeeUpdateRequest);
}