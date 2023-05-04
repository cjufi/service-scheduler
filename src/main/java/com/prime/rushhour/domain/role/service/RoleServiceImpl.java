package com.prime.rushhour.domain.role.service;

import com.prime.rushhour.domain.role.dto.RoleDto;
import com.prime.rushhour.domain.role.entity.Role;
import com.prime.rushhour.domain.role.mapper.RoleMapper;
import com.prime.rushhour.domain.role.repository.RoleRepository;
import com.prime.rushhour.infrastructure.exceptions.DuplicateResourceException;
import com.prime.rushhour.infrastructure.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;
    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleDto save(RoleDto roleDto) {
        if(roleRepository.existsByName(roleDto.name())) {
            throw new DuplicateResourceException("Name", roleDto.name());
        }

        var role = roleMapper.toEntity(roleDto);
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    public RoleDto getById(Long id) {
        var role = roleRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(Role.class.getSimpleName(),"id", id));
        return roleMapper.toDto(role);
    }

    @Override
    public Page<RoleDto> getAll(Pageable pageable) {
        return roleRepository.findAll(pageable).map(roleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        if(!roleRepository.existsById(id)){
            throw new EntityNotFoundException(Role.class.getSimpleName(), "id", id);
        }
        roleRepository.deleteById(id);
    }

    @Override
    public RoleDto update(Long id, RoleDto roleDto) {
        var role = roleRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(Role.class.getSimpleName(),"id", id));

        roleMapper.update(role, roleDto);
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    public String getNameById(Long id) {
        var role = roleRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(Role.class.getSimpleName(),"id", id));
        return role.getName();
    }

    public Role idToRole(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(Role.class.getSimpleName(),"id", id));
    }
}