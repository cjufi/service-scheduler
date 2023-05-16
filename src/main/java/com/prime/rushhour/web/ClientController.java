package com.prime.rushhour.web;

import com.prime.rushhour.domain.account.dto.LoginRequest;
import com.prime.rushhour.domain.client.dto.ClientRequest;
import com.prime.rushhour.domain.client.dto.ClientResponse;
import com.prime.rushhour.domain.client.dto.ClientUpdateRequest;
import com.prime.rushhour.domain.client.service.ClientService;
import com.prime.rushhour.domain.permission.service.PermissionService;
import com.prime.rushhour.domain.permission.service.PermissionServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientResponse> save(@Valid @RequestBody ClientRequest clientRequest) {
        return new ResponseEntity<>(clientService.save(clientRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(clientService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ClientResponse>> getAll(Pageable pageable) {
        return new ResponseEntity<>(clientService.getAll(pageable), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("(hasAuthority('SCOPE_CLIENT') && @permissionServiceImpl.canClientAccessClient(#id)) || hasAuthority('SCOPE_ADMIN')")
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("(hasAuthority('SCOPE_CLIENT') && @permissionServiceImpl.canClientAccessClient(#id)) || hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ClientResponse> update(@PathVariable Long id, @Valid @RequestBody ClientUpdateRequest clientUpdateRequest) {
        return new ResponseEntity<>(clientService.update(id, clientUpdateRequest), HttpStatus.OK);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        return clientService.login(loginRequest.username(), loginRequest.password());
    }
}