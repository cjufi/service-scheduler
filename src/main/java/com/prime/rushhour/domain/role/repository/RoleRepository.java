package com.prime.rushhour.domain.role.repository;

import com.prime.rushhour.domain.role.entity.Role;
import com.prime.rushhour.domain.role.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByName(RoleType roleType);
}