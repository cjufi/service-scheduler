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

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

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

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<AppointmentResponse>> getAll(Pageable pageable) {
        return new ResponseEntity<>(appointmentService.getAll(pageable), HttpStatus.OK);
    }

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