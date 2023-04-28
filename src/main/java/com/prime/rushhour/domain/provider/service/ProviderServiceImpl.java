package com.prime.rushhour.domain.provider.service;

import com.prime.rushhour.domain.provider.dto.ProviderRequest;
import com.prime.rushhour.domain.provider.dto.ProviderResponse;
import com.prime.rushhour.domain.provider.entity.Provider;
import com.prime.rushhour.domain.provider.mapper.ProviderMapper;
import com.prime.rushhour.domain.provider.repository.ProviderRepository;
import com.prime.rushhour.infrastructure.exceptions.DuplicateResourceException;
import com.prime.rushhour.infrastructure.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProviderServiceImpl implements ProviderService{

    private final ProviderRepository providerRepository;

    private final ProviderMapper providerMapper;

    public ProviderServiceImpl(ProviderRepository providerRepository, ProviderMapper providerMapper) {
        this.providerRepository = providerRepository;
        this.providerMapper = providerMapper;
    }

    @Override
    public ProviderResponse save(ProviderRequest providerRequest) {

        if(providerRepository.existsByName(providerRequest.name())){
            throw new DuplicateResourceException("Name", providerRequest.name());
        }
        else if (providerRepository.existsByBusinessDomain(providerRequest.businessDomain())){
            throw new DuplicateResourceException("Business Domain", providerRequest.businessDomain());
        }

        var provider = providerMapper.toEntity(providerRequest);
        return providerMapper.toDto(providerRepository.save(provider));
    }

    @Override
    public ProviderResponse getById(Long id) {
        var provider = providerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Provider.class.getSimpleName(),"id", id));
        return providerMapper.toDto(provider);
    }

    @Override
    public Page<ProviderResponse> getAll(Pageable pageable) {
        return providerRepository.findAll(pageable).map(providerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        if(!providerRepository.existsById(id)){
            throw new EntityNotFoundException(Provider.class.getSimpleName(),"id", id);
        }
        providerRepository.deleteById(id);
    }

    @Override
    public ProviderResponse update(Long id, ProviderRequest providerRequest) {
        var provider = providerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Provider.class.getSimpleName(),"id", id));

        providerMapper.update(provider, providerRequest);
        return providerMapper.toDto(providerRepository.save(provider));
    }

    @Override
    public Provider getProviderById(Long id) {
        var provider = providerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Provider.class.getSimpleName(),"id", id));
        return provider;
    }
}