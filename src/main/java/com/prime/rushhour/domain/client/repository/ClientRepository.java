package com.prime.rushhour.domain.client.repository;

import com.prime.rushhour.domain.account.entity.Account;
import com.prime.rushhour.domain.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c.account.id FROM Client c WHERE c.id = :clientId")
    Long findAccountIdByClientId(@Param("clientId") Long clientId);

    @Query("SELECT c FROM Client c WHERE c.account.id = :#{#account.id}")
    Client findByAccount(@Param("account") Account account);
}
