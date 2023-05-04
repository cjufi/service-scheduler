package com.prime.rushhour.domain.employee.service;

import com.prime.rushhour.domain.employee.dto.EmployeeRequest;
import com.prime.rushhour.domain.employee.dto.EmployeeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

    EmployeeResponse save(EmployeeRequest employeeRequest);

    EmployeeResponse getById(Long id);

    Page<EmployeeResponse> getAll(Pageable pageable);

    void delete(Long id);

    EmployeeResponse update(Long id, EmployeeRequest employeeRequest);

    void deleteByProviderId(Long id);
}