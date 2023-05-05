package com.prime.rushhour.domain.client.service;

import com.prime.rushhour.domain.client.dto.ClientRequest;
import com.prime.rushhour.domain.client.dto.ClientResponse;
import com.prime.rushhour.domain.client.entity.Client;
import com.prime.rushhour.domain.client.mapper.ClientMapper;
import com.prime.rushhour.domain.client.repository.ClientRepository;
import com.prime.rushhour.infrastructure.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientResponse save(ClientRequest clientRequest) {
        var client = clientMapper.toEntity(clientRequest);
        return clientMapper.toDto(clientRepository.save(client));
    }

    @Override
    public ClientResponse getById(Long id) {
        var client = clientRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(Client.class.getSimpleName(), "id", id));
        return clientMapper.toDto(client);
    }

    @Override
    public Page<ClientResponse> getAll(Pageable pageable) {
        return clientRepository.findAll(pageable).map(clientMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        if(!clientRepository.existsById(id)) {
            throw new EntityNotFoundException(Client.class.getSimpleName(), "id", id);
        }
        clientRepository.deleteById(id);
    }

    @Override
    public ClientResponse update(Long id, ClientRequest clientRequest) {
        var client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Client.class.getSimpleName(), "id", id));

        clientMapper.update(client, clientRequest);
        return clientMapper.toDto(clientRepository.save(client));
    }
}