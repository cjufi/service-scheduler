package com.prime.rushhour.domain.client.service;

import com.prime.rushhour.domain.account.service.AccountService;
import com.prime.rushhour.domain.client.dto.ClientRequest;
import com.prime.rushhour.domain.client.dto.ClientResponse;
import com.prime.rushhour.domain.client.dto.ClientUpdateRequest;
import com.prime.rushhour.domain.client.entity.Client;
import com.prime.rushhour.domain.client.mapper.ClientMapper;
import com.prime.rushhour.domain.client.repository.ClientRepository;
import com.prime.rushhour.domain.employee.entity.Employee;
import com.prime.rushhour.domain.role.entity.RoleType;
import com.prime.rushhour.domain.role.service.RoleService;
import com.prime.rushhour.domain.token.service.TokenService;
import com.prime.rushhour.infrastructure.exceptions.EntityNotFoundException;
import com.prime.rushhour.infrastructure.exceptions.RoleNotCompatibleException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    private final AccountService accountService;

    private final RoleService roleService;

    private final TokenService tokenService;

    private final AuthenticationManager manager;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper, AccountService accountService, RoleService roleService, TokenService tokenService, AuthenticationManager manager) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.accountService = accountService;
        this.roleService = roleService;
        this.tokenService = tokenService;
        this.manager = manager;
    }

    @Override
    public ClientResponse save(ClientRequest clientRequest) {
        accountService.validateAccount(clientRequest.accountRequest());

        if (!checkRole(clientRequest.accountRequest().roleId())) {
            throw new RoleNotCompatibleException(Employee.class.getSimpleName(), clientRequest.accountRequest().roleId());
        }

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
    public ClientResponse update(Long id, ClientUpdateRequest clientUpdateRequest) {
        var client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Client.class.getSimpleName(), "id", id));

        if (!checkRole(clientUpdateRequest.accountUpdateRequest().roleId())) {
            throw new RoleNotCompatibleException(Employee.class.getSimpleName(), clientUpdateRequest.accountUpdateRequest().roleId());
        }

        clientMapper.update(client, clientUpdateRequest);
        return clientMapper.toDto(clientRepository.save(client));
    }

    @Override
    public String login(String email, String password) {
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        String token = tokenService.generateToken(authentication);
        return token;
    }

    protected boolean checkRole(Long id) {
        var role = roleService.getById(id);
        var roleType = RoleType.valueOf(role.name().toUpperCase());
        return roleType == RoleType.CLIENT;
    }
}