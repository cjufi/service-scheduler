package com.prime.rushhour.domain.client.mapper;

import com.prime.rushhour.domain.client.dto.ClientRequest;
import com.prime.rushhour.domain.client.dto.ClientResponse;
import com.prime.rushhour.domain.client.dto.ClientUpdateRequest;
import com.prime.rushhour.domain.client.entity.Client;
import com.prime.rushhour.domain.role.service.RoleService;
import com.prime.rushhour.infrastructure.mapper.password.EncodedMapping;

import com.prime.rushhour.infrastructure.mapper.password.PasswordEncoderMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        uses = {RoleService.class, PasswordEncoderMapper.class})
public interface ClientMapper {

    @Mapping(target = "account", source = "account")
    @Mapping(target = "account.role", source = "account.roleId")
    @Mapping(target = "account.password", qualifiedBy = EncodedMapping.class)
    Client toEntity(ClientRequest clientRequest);

    @Mapping(target = "account", source = "account")
    ClientResponse toDto(Client client);

    @Mapping(target = "account", source = "account")
    @Mapping(target = "account.role", source = "account.roleId")
    @Mapping(target = "account.password", qualifiedBy = EncodedMapping.class)
    void update(@MappingTarget Client client, ClientUpdateRequest clientUpdateRequest);
}