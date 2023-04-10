package com.prime.rushhour.domain.provider.mapper;

import com.prime.rushhour.domain.provider.dto.ProviderRequestDto;
import com.prime.rushhour.domain.provider.dto.ProviderResponseDto;
import com.prime.rushhour.domain.provider.entity.Provider;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProviderMapper {

    Provider toEntity(ProviderRequestDto providerRequestDto);

    ProviderResponseDto toDto(Provider provider);

    void update(@MappingTarget Provider provider, ProviderRequestDto providerRequestDto);
}