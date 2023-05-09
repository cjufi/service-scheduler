package com.prime.rushhour.domain.client.service;

import com.prime.rushhour.domain.account.dto.AccountRequest;
import com.prime.rushhour.domain.account.dto.AccountResponse;
import com.prime.rushhour.domain.account.dto.AccountUpdateRequest;
import com.prime.rushhour.domain.account.entity.Account;
import com.prime.rushhour.domain.account.service.AccountService;
import com.prime.rushhour.domain.client.dto.ClientRequest;
import com.prime.rushhour.domain.client.dto.ClientResponse;
import com.prime.rushhour.domain.client.dto.ClientUpdateRequest;
import com.prime.rushhour.domain.client.entity.Client;
import com.prime.rushhour.domain.client.mapper.ClientMapper;
import com.prime.rushhour.domain.client.repository.ClientRepository;
import com.prime.rushhour.domain.role.dto.RoleDto;
import com.prime.rushhour.domain.role.entity.Role;
import com.prime.rushhour.domain.role.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private AccountService accountService;

    @Mock
    private RoleService roleService;

    @Test
    void save() {

        ClientRequest clientRequest = new ClientRequest("0621296070", "Ledinaja 2", new AccountRequest("filip@gmail.com", "FilipTasic", "Password12#", 4L));
        Client client = new Client("0621296070", "Ledinaja 2", new Account("filip@gmail.com", "FilipTasic", "Password12#", new Role("CLIENT")));
        ClientResponse clientResponse = new ClientResponse("0621296070", "Ledinaja 2", new AccountResponse("filip@gmail.com", "FilipTasic", new RoleDto("CLIENT")));

        when(roleService.getById(4L)).thenReturn(new RoleDto("CLIENT"));
        when(clientMapper.toEntity(clientRequest)).thenReturn(client);
        when(clientRepository.save(client)).thenReturn(client);
        when(clientMapper.toDto(client)).thenReturn(clientResponse);

        ClientResponse result = clientService.save(clientRequest);

        assertEquals(clientResponse, result);
        verify(roleService, times(1)).getById(4L);
        verify(clientMapper, times(1)).toEntity(clientRequest);
        verify(clientRepository, times(1)).save(client);
        verify(clientMapper, times(1)).toDto(client);
    }

    @Test
    void getById() {

        Client client = new Client("0621296070", "Ledinaja 2", new Account("filip@gmail.com", "FilipTasic", "Password12#", new Role("CLIENT")));
        ClientResponse clientResponse = new ClientResponse("0621296070", "Ledinaja 2", new AccountResponse("filip@gmail.com", "FilipTasic", new RoleDto("CLIENT")));

        Long id = 1L;
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));
        when(clientMapper.toDto(client)).thenReturn(clientResponse);

        clientResponse = clientService.getById(id);

        assertEquals(client.getPhone(), clientResponse.phone());
        verify(clientRepository).findById(id);
        verify(clientMapper).toDto(client);
    }

    @Test
    void getAll() {

        List<Client> clients = Arrays.asList(
                new Client("0621296070", "Ledinaja 2", new Account("filip@gmail.com", "FilipTasic", "Password12#", new Role("CLIENT"))),
                new Client("0634123555", "Spanskih", new Account("ceca@gmail.com", "SvetlanaStojanovic", "Password123#", new Role("CLIENT")))
        );

        Page<Client> page = new PageImpl<>(clients);

        when(clientRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(clientMapper.toDto(any(Client.class))).thenReturn(new ClientResponse(
                "0621296070", "Ledinaja 2", new AccountResponse("filip@gmail.com", "FilipTasic", new RoleDto("CLIENT"))
        ));

        Page<ClientResponse> result = clientService.getAll(PageRequest.of(0, 10));

        assertEquals(2, result.getContent().size());
    }

    @Test
    void delete() {
        Long id = 1L;

        when(clientRepository.existsById(id)).thenReturn(true);

        clientService.delete(id);

        verify(clientRepository, times(1)).deleteById(id);
    }

    @Test
    void update() {

        ClientUpdateRequest clientUpdateRequest = new ClientUpdateRequest("0621296070", "Ledinaja 2", new AccountUpdateRequest("FilipTasic", "Password12#", 4L));
        Client client = new Client("0621296070", "Ledinaja 2", new Account("filip@gmail.com", "FilipTasic", "Password12#", new Role("CLIENT")));
        ClientResponse clientResponse = new ClientResponse("0621296070", "Ledinaja 2", new AccountResponse("filip@gmail.com", "FilipTasic", new RoleDto("CLIENT")));

        when(roleService.getById(4L)).thenReturn(new RoleDto("CLIENT"));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.save(client)).thenReturn(client);
        when(clientMapper.toDto(client)).thenReturn(clientResponse);

        ClientResponse result = clientService.update(1L, clientUpdateRequest);

        assertEquals(result, clientResponse);

        verify(clientRepository).findById(1L);
        verify(clientMapper).update(client, clientUpdateRequest);
        verify(clientRepository).save(client);
        verify(clientMapper).toDto(client);
    }
}