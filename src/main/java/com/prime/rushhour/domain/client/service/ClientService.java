package com.prime.rushhour.domain.client.service;

import com.prime.rushhour.domain.client.dto.ClientRequest;
import com.prime.rushhour.domain.client.dto.ClientResponse;
import com.prime.rushhour.domain.client.dto.ClientUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {

    ClientResponse save(ClientRequest clientRequest);

    ClientResponse getById(Long id);

    Page<ClientResponse> getAll(Pageable pageable);

    void delete(Long id);

    ClientResponse update(Long id, ClientUpdateRequest clientUpdateRequest);
}