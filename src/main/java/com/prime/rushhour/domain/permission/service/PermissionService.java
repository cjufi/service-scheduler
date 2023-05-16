package com.prime.rushhour.domain.permission.service;

import com.prime.rushhour.domain.employee.dto.EmployeeRequest;

public interface PermissionService {

    boolean canProviderAdminAccessEmployee(Long id);

    boolean canClientAccessClient(Long id);

    boolean canProviderAdminAccessProvider(Long id);

    boolean canProviderAdminCreateEmployee(EmployeeRequest employeeRequest);
}
