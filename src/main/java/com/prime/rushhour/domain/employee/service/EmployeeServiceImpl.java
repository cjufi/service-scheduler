package com.prime.rushhour.domain.employee.service;

import com.prime.rushhour.domain.account.service.AccountService;
import com.prime.rushhour.domain.employee.dto.EmployeeRequest;
import com.prime.rushhour.domain.employee.dto.EmployeeResponse;
import com.prime.rushhour.domain.employee.entity.Employee;
import com.prime.rushhour.domain.employee.mapper.EmployeeMapper;
import com.prime.rushhour.domain.employee.repository.EmployeeRepository;
import com.prime.rushhour.infrastructure.exceptions.DuplicateResourceException;
import com.prime.rushhour.infrastructure.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    private final AccountService accountService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, AccountService accountService) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.accountService = accountService;
    }

    @Override
    public EmployeeResponse save(EmployeeRequest employeeRequest) {

        if(accountService.checkFullName(employeeRequest.accountRequest().fullName())) {
            throw new DuplicateResourceException("Full Name", employeeRequest.accountRequest().fullName());
        }
        else if(accountService.checkEmail(employeeRequest.accountRequest().email())) {
            throw new DuplicateResourceException("Email", employeeRequest.accountRequest().email());
        }

        var employee = employeeMapper.toEntity(employeeRequest);
        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    @Override
    public EmployeeResponse getById(Long id) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Employee.class.getSimpleName(), "id", id));
        return employeeMapper.toDto(employee);
    }

    @Override
    public Page<EmployeeResponse> getAll(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(employeeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        if(!employeeRepository.existsById(id)){
            throw new EntityNotFoundException(Employee.class.getSimpleName(), "id", id);
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeResponse update(Long id, EmployeeRequest employeeRequest) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Employee.class.getSimpleName(), "id", id));

        employeeMapper.update(employee, employeeRequest);
        return employeeMapper.toDto(employeeRepository.save(employee));
    }
}