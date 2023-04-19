package com.prime.rushhour.domain.provider.service;

import com.prime.rushhour.domain.provider.dto.ProviderRequestDto;
import com.prime.rushhour.domain.provider.dto.ProviderResponseDto;
import com.prime.rushhour.domain.provider.entity.Provider;
import com.prime.rushhour.domain.provider.mapper.ProviderMapper;
import com.prime.rushhour.domain.provider.repository.ProviderRepository;
import com.prime.rushhour.infrastructure.exceptions.EntityNotFound;
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
    public ProviderResponseDto save(ProviderRequestDto providerRequestDto) {
        var provider = providerMapper.toEntity(providerRequestDto);
        return providerMapper.toDto(providerRepository.save(provider));
    }

    @Override
    public ProviderResponseDto getById(Long id) {
        var provider = providerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound(Provider.class.getSimpleName(),"id", id));
        return providerMapper.toDto(provider);
    }

    @Override
    public Page<ProviderResponseDto> getAll(Pageable pageable) {
        return providerRepository.findAll(pageable).map(providerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        if(!providerRepository.existsById(id)){
            throw new EntityNotFound(Provider.class.getSimpleName(),"id", id);
        }
        providerRepository.deleteById(id);
    }

    @Override
    public ProviderResponseDto update(Long id, ProviderRequestDto providerRequestDto) {
        var provider = providerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound(Provider.class.getSimpleName(),"id", id));

        providerMapper.update(provider, providerRequestDto);
        providerRepository.save(provider);
        return providerMapper.toDto(provider);
    }
}
