package com.prime.rushhour.domain.role.service;

import com.prime.rushhour.domain.role.dto.RoleDto;
import com.prime.rushhour.domain.role.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {

    RoleDto save(RoleDto roleDto);

    RoleDto getById(Long id);

    Page<RoleDto> getAll(Pageable pageable);

    RoleDto update(Long id, RoleDto roleDto);

    void delete(Long id);

    String getNameById(Long id);

    Role idToRole(Long id);
}