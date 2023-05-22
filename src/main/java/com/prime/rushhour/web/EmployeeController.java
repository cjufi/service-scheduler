package com.prime.rushhour.web;

import com.prime.rushhour.domain.employee.dto.EmployeeRequest;
import com.prime.rushhour.domain.employee.dto.EmployeeResponse;
import com.prime.rushhour.domain.employee.dto.EmployeeUpdateRequest;
import com.prime.rushhour.domain.employee.service.EmployeeService;
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
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @PreAuthorize("(hasRole('PROVIDER_ADMIN') && @permissionService.canProviderAdminCreateEmployee(#employeeRequest)) || hasRole('ADMIN')")
    public ResponseEntity<EmployeeResponse> save(@Valid @RequestBody EmployeeRequest employeeRequest) {
        return new ResponseEntity<>(employeeService.save(employeeRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize(" (hasRole('EMPLOYEE') && @permissionService.canEmployeeAccessEmployee(#id)) || (hasRole('PROVIDER_ADMIN') && @permissionService.canProviderAdminAccessEmployee(#id)) || hasRole('ADMIN')")
    public ResponseEntity<EmployeeResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(employeeService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<EmployeeResponse>> getAll(Pageable pageable) {
        return new ResponseEntity<>(employeeService.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/providerEmployees")
    @PreAuthorize("hasRole('PROVIDER_ADMIN')")
    public ResponseEntity<Page<EmployeeResponse>> getAllFromProvider(@AuthenticationPrincipal CustomUserDetails user, Pageable pageable) {
        return new ResponseEntity<>(employeeService.getAllFromSameProvider(pageable, user.getAccount().getId()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("(hasRole('PROVIDER_ADMIN') && @permissionService.canProviderAdminAccessEmployee(#id)) || hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        employeeService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("(hasRole('EMPLOYEE') && @permissionService.canEmployeeAccessEmployee(#id)) || (hasRole('PROVIDER_ADMIN') && @permissionService.canProviderAdminAccessEmployee(#id)) || hasRole('ADMIN')")
    public ResponseEntity<EmployeeResponse> update(@PathVariable Long id, @Valid @RequestBody EmployeeUpdateRequest employeeUpdateRequest) {
        return new ResponseEntity<>(employeeService.update(id, employeeUpdateRequest), HttpStatus.OK);
    }
}