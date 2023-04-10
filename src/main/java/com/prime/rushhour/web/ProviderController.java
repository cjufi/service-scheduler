package com.prime.rushhour.web;

import com.prime.rushhour.domain.provider.dto.ProviderRequestDto;
import com.prime.rushhour.domain.provider.dto.ProviderResponseDto;
import com.prime.rushhour.domain.provider.service.ProviderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/provider")
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping
    public ResponseEntity<ProviderResponseDto> save(@Valid @RequestBody ProviderRequestDto providerRequestDto) {
        return new ResponseEntity<>(providerService.save(providerRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderResponseDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(providerService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ProviderResponseDto>> getAll(Pageable pageable) {
        return new ResponseEntity<>(providerService.getAll(pageable), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        providerService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderResponseDto> update(@PathVariable Long id, @Valid @RequestBody ProviderRequestDto providerRequestDto) {
        return new ResponseEntity<>(providerService.update(id, providerRequestDto), HttpStatus.OK);
    }
}