package com.prime.rushhour.domain.provider.service;

import com.prime.rushhour.domain.provider.dto.ProviderRequest;
import com.prime.rushhour.domain.provider.dto.ProviderResponse;
import com.prime.rushhour.domain.provider.entity.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProviderService {

    ProviderResponse save(ProviderRequest providerRequest);

    ProviderResponse getById(Long id);

    Page<ProviderResponse> getAll(Pageable pageable);

    void delete(Long id);

    ProviderResponse update(Long id, ProviderRequest providerRequest);

    Provider getProviderById(Long id);

    Long getProviderIdByAccount(Long id);
}