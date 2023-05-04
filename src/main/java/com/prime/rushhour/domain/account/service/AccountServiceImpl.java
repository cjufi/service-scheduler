package com.prime.rushhour.domain.account.service;

import com.prime.rushhour.domain.account.dto.AccountRequest;
import com.prime.rushhour.domain.account.repository.AccountRepository;
import com.prime.rushhour.infrastructure.exceptions.DuplicateResourceException;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean checkFullName(String name) {
        return accountRepository.existsByFullName(name);
    }

    public boolean checkEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public void validateAccount(AccountRequest accountRequest) {
        if(checkFullName(accountRequest.fullName())){
            throw new DuplicateResourceException("Full Name", accountRequest.fullName());
        }
        if(checkEmail(accountRequest.email())){
            throw new DuplicateResourceException("Email", accountRequest.email());
        }
    }
}