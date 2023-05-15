package com.prime.rushhour.domain.employee.repository;

import com.prime.rushhour.domain.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    void deleteEmployeesByProviderId(Long id);

    Long findByProviderId(Long id);
}
