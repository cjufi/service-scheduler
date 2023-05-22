package com.prime.rushhour.domain.account.service;

import com.prime.rushhour.domain.account.dto.AccountRequest;
import com.prime.rushhour.domain.account.entity.Account;

public interface AccountService {

    boolean checkFullName(String name);

    boolean checkEmail(String email);

    void validateAccount(AccountRequest accountRequest);

    Account findByEmail(String email);
}
