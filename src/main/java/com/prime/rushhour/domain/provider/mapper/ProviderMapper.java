package com.prime.rushhour.domain.provider.mapper;

import com.prime.rushhour.domain.provider.dto.ProviderRequest;
import com.prime.rushhour.domain.provider.dto.ProviderResponse;
import com.prime.rushhour.domain.provider.entity.Provider;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ProviderMapper {

    Provider toEntity(ProviderRequest providerRequest);

    ProviderResponse toDto(Provider provider);

    void update(@MappingTarget Provider provider, ProviderRequest providerRequest);
}