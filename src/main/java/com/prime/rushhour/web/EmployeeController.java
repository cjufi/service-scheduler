package com.prime.rushhour.web;

import com.prime.rushhour.domain.account.dto.LoginRequest;
import com.prime.rushhour.domain.employee.dto.EmployeeRequest;
import com.prime.rushhour.domain.employee.dto.EmployeeResponse;
import com.prime.rushhour.domain.employee.dto.EmployeeUpdateRequest;
import com.prime.rushhour.domain.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @PreAuthorize("(hasAuthority('SCOPE_PROVIDER_ADMIN') && @permissionServiceImpl.canProviderAdminCreateEmployee(#employeeRequest)) || hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<EmployeeResponse> save(@Valid @RequestBody EmployeeRequest employeeRequest) {
        return new ResponseEntity<>(employeeService.save(employeeRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize(" (hasAuthority('SCOPE_EMPLOYEE') && @permissionServiceImpl.canEmployeeAccessEmployee(#id)) || (hasAuthority('SCOPE_PROVIDER_ADMIN') && @permissionServiceImpl.canProviderAdminAccessEmployee(#id)) || hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<EmployeeResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(employeeService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeResponse>> getAll(Pageable pageable) {
        return new ResponseEntity<>(employeeService.getAll(pageable), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("(hasAuthority('SCOPE_PROVIDER_ADMIN') && @permissionServiceImpl.canProviderAdminAccessEmployee(#id)) || hasAuthority('SCOPE_ADMIN')")
    public void delete(@PathVariable Long id) {
        employeeService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("(hasAuthority('SCOPE_EMPLOYEE') && @permissionServiceImpl.canEmployeeAccessEmployee(#id)) || (hasAuthority('SCOPE_PROVIDER_ADMIN') && @permissionServiceImpl.canProviderAdminAccessEmployee(#id)) || hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<EmployeeResponse> update(@PathVariable Long id, @Valid @RequestBody EmployeeUpdateRequest employeeUpdateRequest) {
        return new ResponseEntity<>(employeeService.update(id, employeeUpdateRequest), HttpStatus.OK);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        return employeeService.login(loginRequest.username(), loginRequest.password());
    }
}