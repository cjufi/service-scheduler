package com.prime.rushhour.domain.client.mapper;

import com.prime.rushhour.domain.client.dto.ClientRequest;
import com.prime.rushhour.domain.client.dto.ClientResponse;
import com.prime.rushhour.domain.client.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "account", source = "accountRequest")
    Client toEntity(ClientRequest clientRequest);

    @Mapping(target = "accountResponse", source = "account")
    ClientResponse toDto(Client client);

    @Mapping(target = "account", source = "accountRequest")
    void update(@MappingTarget Client client, ClientRequest clientRequest);
}