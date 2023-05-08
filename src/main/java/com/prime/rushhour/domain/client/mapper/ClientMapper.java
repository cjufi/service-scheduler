package com.prime.rushhour.domain.client.mapper;

import com.prime.rushhour.domain.client.dto.ClientRequest;
import com.prime.rushhour.domain.client.dto.ClientResponse;
import com.prime.rushhour.domain.client.dto.ClientUpdateRequest;
import com.prime.rushhour.domain.client.entity.Client;
import com.prime.rushhour.domain.role.service.RoleService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {RoleService.class})
public interface ClientMapper {

    @Mapping(target = "account", source = "accountRequest")
    @Mapping(target = "account.role", source = "accountRequest.roleId")
    Client toEntity(ClientRequest clientRequest);

    @Mapping(target = "accountResponse", source = "account")
    ClientResponse toDto(Client client);

    @Mapping(target = "account", source = "accountUpdateRequest")
    @Mapping(target = "account.role", source = "accountUpdateRequest.roleId")
    void update(@MappingTarget Client client, ClientUpdateRequest clientUpdateRequest);
}