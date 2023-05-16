package com.prime.rushhour.domain.permission.service;

import org.springframework.stereotype.Component;

@Component
public interface PermissionService {

    boolean canProviderAdminAccessEmployee(Long id);
}
