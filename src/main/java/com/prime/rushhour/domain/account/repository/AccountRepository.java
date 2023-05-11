package com.prime.rushhour.domain.account.repository;

import com.prime.rushhour.domain.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByFullName(String fullName);

    boolean existsByEmail(String email);

    Account findByEmail(String email);
}