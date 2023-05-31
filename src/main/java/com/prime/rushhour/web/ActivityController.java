package com.prime.rushhour.web;

import com.prime.rushhour.domain.activity.dto.ActivityRequest;
import com.prime.rushhour.domain.activity.dto.ActivityResponse;
import com.prime.rushhour.domain.activity.service.ActivityService;
import com.prime.rushhour.infrastructure.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/activity")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    @PreAuthorize("(hasRole('PROVIDER_ADMIN') && " +
            "@permissionService.canProviderAdminAccessProvider(#activityRequest.providerId()) && " +
            "@permissionService.canProviderAdminAccessEmployees(#activityRequest.employeeIds())) || " +
            "hasRole('ADMIN')")
    public ResponseEntity<ActivityResponse> save(@Valid @RequestBody ActivityRequest activityRequest) {
        return new ResponseEntity<>(activityService.save(activityRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("(hasRole('PROVIDER_ADMIN') && " +
            "@permissionService.canProviderAdminAccessActivity(#id)) || " +
            "hasRole('CLIENT') || " +
            "hasRole('ADMIN')")
    public ResponseEntity<ActivityResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(activityService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("(hasRole('CLIENT')) || " +
            "hasRole('ADMIN')")
    public ResponseEntity<Page<ActivityResponse>> getAll(Pageable pageable) {
        return new ResponseEntity<>(activityService.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/providerActivities")
    @PreAuthorize("hasRole('PROVIDER_ADMIN')")
    public ResponseEntity<Page<ActivityResponse>> getAllFromProvider(@AuthenticationPrincipal CustomUserDetails user, Pageable pageable) {
        return new ResponseEntity<>(activityService.getAllFromSameProvider(pageable, user.getAccount().getId()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CONFLICT)
    @PreAuthorize("(hasRole('PROVIDER_ADMIN') && " +
            "@permissionService.canProviderAdminAccessActivity(#id)) || " +
            "hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        activityService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("(hasRole('PROVIDER_ADMIN') && " +
            "@permissionService.canProviderAdminAccessActivity(#id) &&" +
            "@permissionService.canProviderAdminAccessEmployees(#activityRequest.employeeIds()) &&" +
            "@permissionService.canProviderAdminAccessProvider(#activityRequest.providerId())) || " +
            "hasRole('ADMIN')")
    public ResponseEntity<ActivityResponse> update(@PathVariable Long id, @Valid @RequestBody ActivityRequest activityRequest) {
        return new ResponseEntity<>(activityService.update(id, activityRequest), HttpStatus.OK);
    }
}