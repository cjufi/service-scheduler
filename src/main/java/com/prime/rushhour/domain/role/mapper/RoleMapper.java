package com.prime.rushhour.domain.role.mapper;

import com.prime.rushhour.domain.role.dto.RoleDto;
import com.prime.rushhour.domain.role.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toEntity(RoleDto roleDto);

    RoleDto toDto(Role role);

    void update(@MappingTarget Role role, RoleDto roleDto);

}