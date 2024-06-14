package com.prime.rushhour.web;

import com.prime.rushhour.domain.provider.dto.ProviderRequest;
import com.prime.rushhour.domain.provider.dto.ProviderResponse;
import com.prime.rushhour.domain.provider.service.ProviderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing providers.
 *
 * Provides endpoints for creating, retrieving, updating, and deleting provider records.
 *
 * @version 1.0
 * @author Filip
 */
@RestController
@RequestMapping("/api/v1/provider")
public class ProviderController {

    private final ProviderService providerService;

    /**
     * Constructs a new ProviderController with the specified ProviderService.
     *
     * @param providerService the service for managing providers
     */
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    /**
     * Creates a new provider.
     *
     * @param providerRequest the request body containing provider details
     * @return the created provider response
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProviderResponse> save(@Valid @RequestBody ProviderRequest providerRequest) {
        return new ResponseEntity<>(providerService.save(providerRequest), HttpStatus.CREATED);
    }

    /**
     * Retrieves a provider by its ID.
     *
     * @param id the ID of the provider to retrieve
     * @return the provider response
     */
    @GetMapping("/{id}")
    @PreAuthorize("(hasRole('PROVIDER_ADMIN') && @permissionService.canProviderAdminAccessProvider(#id)) || " +
            "hasRole('ADMIN') || hasRole('CLIENT')")
    public ResponseEntity<ProviderResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(providerService.getById(id), HttpStatus.OK);
    }

    /**
     * Retrieves all providers with pagination support.
     *
     * @param pageable the pagination information
     * @return a page of provider responses
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
    public ResponseEntity<Page<ProviderResponse>> getAll(Pageable pageable) {
        return new ResponseEntity<>(providerService.getAll(pageable), HttpStatus.OK);
    }

    /**
     * Deletes a provider by its ID.
     *
     * @param id the ID of the provider to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("(hasRole('PROVIDER_ADMIN') && @permissionService.canProviderAdminAccessProvider(#id)) || hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        providerService.delete(id);
    }

    /**
     * Updates a provider by its ID.
     *
     * @param id the ID of the provider to update
     * @param providerRequest the request body containing updated provider details
     * @return the updated provider response
     */
    @PutMapping("/{id}")
    @PreAuthorize("(hasRole('PROVIDER_ADMIN') && @permissionService.canProviderAdminAccessProvider(#id)) || hasRole('ADMIN')")
    public ResponseEntity<ProviderResponse> update(@PathVariable Long id, @Valid @RequestBody ProviderRequest providerRequest) {
        return new ResponseEntity<>(providerService.update(id, providerRequest), HttpStatus.OK);
    }
}
