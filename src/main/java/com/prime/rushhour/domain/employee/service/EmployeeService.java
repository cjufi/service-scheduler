package com.prime.rushhour.domain.employee.service;

import com.prime.rushhour.domain.employee.dto.EmployeeRequest;
import com.prime.rushhour.domain.employee.dto.EmployeeResponse;
import com.prime.rushhour.domain.employee.dto.EmployeeUpdateRequest;
import com.prime.rushhour.domain.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse save(EmployeeRequest employeeRequest);

    EmployeeResponse getById(Long id);

    Page<EmployeeResponse> getAll(Pageable pageable);

    void delete(Long id);

    EmployeeResponse update(Long id, EmployeeUpdateRequest employeeUpdateRequest);

    Long getProviderIdFromAccount(Long accountId);

    Long getAccountIdFromEmployeeId(Long id);

    Page<EmployeeResponse> getAllFromSameProvider(Pageable pageable, Long id);

    Long getProviderIdFromEmployeeId(Long id);

    List<Employee> idsToEmployees(List<Long> employeeIds);

    List<Long> EmployeesToIds(List<Employee> employees);
}