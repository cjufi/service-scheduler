package com.prime.rushhour.domain.permission.service;

public interface PermissionService {

    boolean canProviderAdminAccessEmployee(Long id);

    boolean canClientAccessClient(Long id);

    boolean canProviderAdminAccessProvider(Long id);
}
