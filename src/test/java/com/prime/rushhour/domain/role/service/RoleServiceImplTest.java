package com.prime.rushhour.domain.role.service;

import com.prime.rushhour.domain.role.dto.RoleDto;
import com.prime.rushhour.domain.role.entity.Role;
import com.prime.rushhour.domain.role.entity.RoleType;
import com.prime.rushhour.domain.role.mapper.RoleMapper;
import com.prime.rushhour.domain.role.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;


    @Test
    void save() {

        RoleDto roleDto = new RoleDto(RoleType.ADMIN);
        Role role = new Role(RoleType.ADMIN);

        when(roleMapper.toEntity(roleDto)).thenReturn(role);
        when(roleRepository.save(role)).thenReturn(role);
        when(roleMapper.toDto(role)).thenReturn(roleDto);

        RoleDto result = roleService.save(roleDto);

        assertEquals(roleDto, result);

        verify(roleMapper).toEntity(roleDto);
        verify(roleRepository).save(role);
        verify(roleMapper).toDto(role);
    }

    @Test
    void getById() {

        Role role = new Role(RoleType.CLIENT);
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        RoleDto roleDto = new RoleDto(RoleType.CLIENT);
        when(roleMapper.toDto(role)).thenReturn(roleDto);

        roleDto = roleService.getById(1L);
        assertEquals(role.getName(), roleDto.name());
    }

    @Test
    void getAll() {

        List<Role> roles = Arrays.asList(
                new Role(RoleType.ADMIN),
                new Role(RoleType.EMPLOYEE)
        );

        Page<Role> page = new PageImpl<>(roles);

        when(roleRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(roleMapper.toDto(any(Role.class))).thenReturn(new RoleDto(RoleType.ADMIN));

        Page<RoleDto> result = roleService.getAll(PageRequest.of(0, 10));

        assertEquals(2, result.getContent().size());
    }

    @Test
    void delete() {

        Long roleId = 1L;

        when(roleRepository.existsById(roleId)).thenReturn(true);

        roleService.delete(roleId);

        verify(roleRepository, times(1)).deleteById(roleId);
    }

    @Test
    void update() {

        Role role = new Role(RoleType.CLIENT);
        RoleDto roleDto = new RoleDto(RoleType.CLIENT);

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(roleRepository.save(role)).thenReturn(role);
        when(roleMapper.toDto(role)).thenReturn(roleDto);

        RoleDto result = roleService.update(1L, roleDto);

        assertEquals(result, roleDto);

        verify(roleRepository).findById(1L);
        verify(roleMapper).update(role, roleDto);
        verify(roleRepository).save(role);
        verify(roleMapper).toDto(role);
    }
}