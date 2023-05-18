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

@RestController
@RequestMapping("/api/v1/provider")
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProviderResponse> save(@Valid @RequestBody ProviderRequest providerRequest) {
        return new ResponseEntity<>(providerService.save(providerRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("(hasRole('PROVIDER_ADMIN') && @permissionService.canProviderAdminAccessProvider(#id)) || hasRole('ADMIN') || hasRole('CLIENT')")
    public ResponseEntity<ProviderResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(providerService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') || hasRole('CLIENT')")
    public ResponseEntity<Page<ProviderResponse>> getAll(Pageable pageable) {
        return new ResponseEntity<>(providerService.getAll(pageable), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("(hasRole('PROVIDER_ADMIN') && @permissionService.canProviderAdminAccessProvider(#id)) || hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        providerService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("(hasRole('PROVIDER_ADMIN') && @permissionService.canProviderAdminAccessProvider(#id)) || hasRole('ADMIN')")
    public ResponseEntity<ProviderResponse> update(@PathVariable Long id, @Valid @RequestBody ProviderRequest providerRequest) {
        return new ResponseEntity<>(providerService.update(id, providerRequest), HttpStatus.OK);
    }
}