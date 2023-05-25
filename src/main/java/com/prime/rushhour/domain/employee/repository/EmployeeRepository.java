package com.prime.rushhour.domain.employee.repository;

import com.prime.rushhour.domain.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    void deleteEmployeesByProviderId(Long id);

    @Query("SELECT e.provider.id FROM Employee e WHERE e.account.id = :accountId")
    Long findProviderIdByAccountId(@Param("accountId") Long accountId);

    @Query("SELECT e.account.id FROM Employee e WHERE e.id = :employeeId")
    Long findAccountIdByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT e.provider.id FROM Employee e WHERE e.id = :employeeId")
    Long findProviderIdByEmployeeId(Long employeeId);

    Page<Employee> findEmployeesByProviderId(Pageable pageable, Long id);

    Long findEmployeeId(Employee employee);
}