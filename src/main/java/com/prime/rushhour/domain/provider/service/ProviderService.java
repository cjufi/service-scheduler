package com.prime.rushhour.domain.provider.service;

import com.prime.rushhour.domain.provider.dto.ProviderRequestDto;
import com.prime.rushhour.domain.provider.dto.ProviderResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProviderService {

    ProviderResponseDto save(ProviderRequestDto providerRequestDto);

    ProviderResponseDto getById(Long id);

    Page<ProviderResponseDto> getAll(Pageable pageable);

    void delete(Long id);

    ProviderResponseDto update(Long id, ProviderRequestDto providerRequestDto);
}