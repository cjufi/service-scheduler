package com.prime.rushhour.web;

import com.prime.rushhour.domain.appointment.dto.AppointmentRequest;
import com.prime.rushhour.domain.appointment.dto.AppointmentResponse;
import com.prime.rushhour.domain.appointment.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing appointments.
 *
 * Provides endpoints for creating, retrieving, updating, and deleting appointments.
 *
 * @version 1.0
 * @author Filip
 */
@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    /**
     * Constructs a new AppointmentController with the specified AppointmentService.
     *
     * @param appointmentService the service for managing appointments
     */
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * Creates a new appointment.
     *
     * @param appointmentRequest the request body containing appointment details
     * @return the created appointment response
     */
    @PostMapping
    @PreAuthorize("(hasRole('EMPLOYEE') && " +
            "@permissionService.canEmployeeAccessActivity(#appointmentRequest.activityIds()) &&" +
            "@permissionService.canEmployeeAccessEmployee(#appointmentRequest.employeeId())) ||" +
            "(hasRole('PROVIDER_ADMIN') && " +
            "@permissionService.canProviderAdminAccessEmployee(#appointmentRequest.employeeId()) &&" +
            "@permissionService.canEmployeeAccessActivity(#appointmentRequest.activityIds())) ||"  +
            "(hasRole('CLIENT') &&" +
            "@permissionService.canClientAccessAppointment(#appointmentRequest.clientId())) ||" +
            "hasRole('ADMIN')")
    public ResponseEntity<AppointmentResponse> save(@Valid @RequestBody AppointmentRequest appointmentRequest) {
        return new ResponseEntity<>(appointmentService.save(appointmentRequest), HttpStatus.CREATED);
    }

    /**
     * Retrieves an appointment by its ID.
     *
     * @param id the ID of the appointment to retrieve
     * @return the appointment response
     */
    @GetMapping("/{id}")
    @PreAuthorize("(hasRole('EMPLOYEE') &&" +
            "@permissionService.canEmployeeAccessAppointment(#id)) ||" +
            "(hasRole('CLIENT') &&" +
            "@permissionService.canClientAccessAppointment(#id)) ||" +
            "(hasRole('PROVIDER_ADMIN') && " +
            "@permissionService.canEmployeeAccessAppointment(#id)) ||"  +
            "hasRole('ADMIN')")
    public ResponseEntity<AppointmentResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(appointmentService.getById(id), HttpStatus.OK);
    }

    /**
     * Retrieves all appointments.
     *
     * @param pageable pagination information
     * @return a page of appointment responses
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<AppointmentResponse>> getAll(Pageable pageable) {
        return new ResponseEntity<>(appointmentService.getAll(pageable), HttpStatus.OK);
    }

    /**
     * Deletes an appointment by its ID.
     *
     * @param id the ID of the appointment to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("(hasRole('EMPLOYEE') &&" +
            "@permissionService.canEmployeeAccessAppointment(#id)) ||" +
            "(hasRole('PROVIDER_ADMIN') && " +
            "@permissionService.canEmployeeAccessAppointment(#id)) ||"  +
            "(hasRole('CLIENT') &&" +
            "@permissionService.canClientAccessAppointment(#id)) ||" +
            "hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        appointmentService.delete(id);
    }

    /**
     * Updates an appointment by its ID.
     *
     * @param id the ID of the appointment to update
     * @param appointmentRequest the request body containing updated appointment details
     * @return the updated appointment response
     */
    @PutMapping("/{id}")
    @PreAuthorize("(hasRole('EMPLOYEE') &&" +
            "@permissionService.canEmployeeAccessAppointment(#id) &&" +
            "@permissionService.canEmployeeAccessEmployee(#appointmentRequest.employeeId()) &&" +
            "@permissionService.canEmployeeAccessActivity(#appointmentRequest.activityIds())) ||" +
            "(hasRole('CLIENT') &&" +
            "@permissionService.canClientAccessAppointment(#id)) ||" +
            "(hasRole('PROVIDER_ADMIN') && " +
            "@permissionService.canEmployeeAccessAppointment(#id)) ||"  +
            "hasRole('ADMIN')")
    public ResponseEntity<AppointmentResponse> update(@PathVariable Long id, @Valid @RequestBody AppointmentRequest appointmentRequest) {
        return new ResponseEntity<>(appointmentService.update(id, appointmentRequest), HttpStatus.OK);
    }
}
