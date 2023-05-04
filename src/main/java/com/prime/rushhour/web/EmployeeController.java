package com.prime.rushhour.web;

import com.prime.rushhour.domain.employee.dto.EmployeeRequest;
import com.prime.rushhour.domain.employee.dto.EmployeeResponse;
import com.prime.rushhour.domain.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> save(@Valid @RequestBody EmployeeRequest employeeRequest) {
        return new ResponseEntity<>(employeeService.save(employeeRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(employeeService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeResponse>> getAll(Pageable pageable) {
        return new ResponseEntity<>(employeeService.getAll(pageable), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        employeeService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> update(@PathVariable Long id, @Valid @RequestBody EmployeeRequest employeeRequest) {
        return new ResponseEntity<>(employeeService.update(id, employeeRequest), HttpStatus.OK);
    }
}