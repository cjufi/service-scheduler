package com.prime.rushhour.domain.account.service;

import com.prime.rushhour.domain.account.dto.AccountRequest;

public interface AccountService {

    boolean checkFullName(String name);

    boolean checkEmail(String email);

    void validateAccount(AccountRequest accountRequest);
}
