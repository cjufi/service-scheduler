package com.prime.rushhour.domain.employee.mapper;

import com.prime.rushhour.domain.employee.dto.EmployeeRequest;
import com.prime.rushhour.domain.employee.dto.EmployeeResponse;
import com.prime.rushhour.domain.employee.dto.EmployeeUpdateRequest;
import com.prime.rushhour.domain.employee.entity.Employee;
import com.prime.rushhour.domain.provider.service.ProviderService;
import com.prime.rushhour.domain.role.service.RoleService;
import com.prime.rushhour.infrastructure.mapper.password.EncodedMapping;
import com.prime.rushhour.infrastructure.mapper.password.PasswordEncoderMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        uses = {RoleService.class, ProviderService.class, PasswordEncoderMapper.class})
public interface EmployeeMapper {

    @Mapping(target = "provider", source = "providerId")
    @Mapping(target = "account.role", source = "account.roleId")
    @Mapping(target = "account", source = "account")
    @Mapping(target = "account.password", qualifiedBy = EncodedMapping.class)
    Employee toEntity(EmployeeRequest employeeRequest);

    @Mapping(target = "provider", source = "provider")
    @Mapping(target = "account.role", source = "account.role")
    @Mapping(target = "account", source = "account")
    EmployeeResponse toDto(Employee employee);

    @Mapping(target = "account.role", source = "account.roleId")
    @Mapping(target = "account", source = "account")
    @Mapping(target = "account.password", qualifiedBy = EncodedMapping.class)
    void update(@MappingTarget Employee employee, EmployeeUpdateRequest employeeUpdateRequest);
}