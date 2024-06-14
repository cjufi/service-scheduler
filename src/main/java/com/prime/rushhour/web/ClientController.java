package com.prime.rushhour.web;

import com.prime.rushhour.domain.client.dto.ClientRequest;
import com.prime.rushhour.domain.client.dto.ClientResponse;
import com.prime.rushhour.domain.client.dto.ClientUpdateRequest;
import com.prime.rushhour.domain.client.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing clients.
 *
 * Provides endpoints for creating, retrieving, updating, and deleting client records.
 *
 * @version 1.0
 * @author Filip
 */
@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    private final ClientService clientService;

    /**
     * Constructs a new ClientController with the specified ClientService.
     *
     * @param clientService the service for managing clients
     */
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Creates a new client.
     *
     * @param clientRequest the request body containing client details
     * @return the created client response
     */
    @PostMapping
    public ResponseEntity<ClientResponse> save(@Valid @RequestBody ClientRequest clientRequest) {
        return new ResponseEntity<>(clientService.save(clientRequest), HttpStatus.CREATED);
    }

    /**
     * Retrieves a client by its ID.
     *
     * @param id the ID of the client to retrieve
     * @return the client response
     */
    @GetMapping("/{id}")
    @PreAuthorize("(hasRole('CLIENT') && @permissionService.canClientAccessClient(#id)) || hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(clientService.getById(id), HttpStatus.OK);
    }

    /**
     * Retrieves all clients with pagination support.
     *
     * @param pageable the pagination information
     * @return a page of client responses
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ClientResponse>> getAll(Pageable pageable) {
        return new ResponseEntity<>(clientService.getAll(pageable), HttpStatus.OK);
    }

    /**
     * Deletes a client by its ID.
     *
     * @param id the ID of the client to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("(hasRole('CLIENT') && @permissionService.canClientAccessClient(#id)) || hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }

    /**
     * Updates a client by its ID.
     *
     * @param id the ID of the client to update
     * @param clientUpdateRequest the request body containing updated client details
     * @return the updated client response
     */
    @PutMapping("/{id}")
    @PreAuthorize("(hasRole('CLIENT') && @permissionService.canClientAccessClient(#id)) || hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> update(@PathVariable Long id, @Valid @RequestBody ClientUpdateRequest clientUpdateRequest) {
        return new ResponseEntity<>(clientService.update(id, clientUpdateRequest), HttpStatus.OK);
    }
}
