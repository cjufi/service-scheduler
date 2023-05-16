package com.prime.rushhour.domain.provider.repository;

import com.prime.rushhour.domain.provider.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

    boolean existsByName(String name);

    boolean existsByBusinessDomain(String businessDomain);

    @Query("SELECT e.provider.id FROM Employee e WHERE e.account.id = :accountId")
    Long findProviderIdByAccountId(@Param("accountId") Long accountId);
}
