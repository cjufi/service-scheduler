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

/**
 * REST controller for managing activities.
 *
 * Provides endpoints for creating, retrieving, updating, and deleting activities.
 *
 * @version 1.0
 * @author Filip
 */
@RestController
@RequestMapping("/api/v1/activity")
public class ActivityController {

    private final ActivityService activityService;

    /**
     * Constructs a new ActivityController with the specified ActivityService.
     *
     * @param activityService the service for managing activities
     */
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    /**
     * Creates a new activity.
     *
     * @param activityRequest the request body containing activity details
     * @return the created activity response
     */
    @PostMapping
    @PreAuthorize("(hasRole('PROVIDER_ADMIN') && " +
            "@permissionService.canProviderAdminAccessProvider(#activityRequest.providerId()) && " +
            "@permissionService.canProviderAdminAccessEmployees(#activityRequest.employeeIds())) || " +
            "hasRole('ADMIN')")
    public ResponseEntity<ActivityResponse> save(@Valid @RequestBody ActivityRequest activityRequest) {
        return new ResponseEntity<>(activityService.save(activityRequest), HttpStatus.CREATED);
    }

    /**
     * Retrieves an activity by its ID.
     *
     * @param id the ID of the activity to retrieve
     * @return the activity response
     */
    @GetMapping("/{id}")
    @PreAuthorize("(hasRole('PROVIDER_ADMIN') && " +
            "@permissionService.canProviderAdminAccessActivity(#id)) || " +
            "hasRole('CLIENT') || " +
            "hasRole('ADMIN')")
    public ResponseEntity<ActivityResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(activityService.getById(id), HttpStatus.OK);
    }

    /**
     * Retrieves all activities.
     *
     * @param pageable pagination information
     * @return a page of activity responses
     */
    @GetMapping
    @PreAuthorize("hasRole('CLIENT') || hasRole('ADMIN')")
    public ResponseEntity<Page<ActivityResponse>> getAll(Pageable pageable) {
        return new ResponseEntity<>(activityService.getAll(pageable), HttpStatus.OK);
    }

    /**
     * Retrieves all activities from the same provider as the authenticated user.
     *
     * @param user the authenticated user
     * @param pageable pagination information
     * @return a page of activity responses
     */
    @GetMapping("/providerActivities")
    @PreAuthorize("hasRole('PROVIDER_ADMIN')")
    public ResponseEntity<Page<ActivityResponse>> getAllFromProvider(@AuthenticationPrincipal CustomUserDetails user, Pageable pageable) {
        return new ResponseEntity<>(activityService.getAllFromSameProvider(pageable, user.getAccount().getId()), HttpStatus.OK);
    }

    /**
     * Deletes an activity by its ID.
     *
     * @param id the ID of the activity to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("(hasRole('PROVIDER_ADMIN') && " +
            "@permissionService.canProviderAdminAccessActivity(#id)) || " +
            "hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        activityService.delete(id);
    }

    /**
     * Updates an activity by its ID.
     *
     * @param id the ID of the activity to update
     * @param activityRequest the request body containing updated activity details
     * @return the updated activity response
     */
    @PutMapping("/{id}")
    @PreAuthorize("(hasRole('PROVIDER_ADMIN') && " +
            "@permissionService.canProviderAdminAccessActivity(#id) &&" +
            "@permissionService.canProviderAdminAccessEmployees(#activityRequest.employeeIds())) || " +
            "hasRole('ADMIN')")
    public ResponseEntity<ActivityResponse> update(@PathVariable Long id, @Valid @RequestBody ActivityRequest activityRequest) {
        return new ResponseEntity<>(activityService.update(id, activityRequest), HttpStatus.OK);
    }
}
