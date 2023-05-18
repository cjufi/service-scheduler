package com.prime.rushhour.infrastructure.security;

import com.prime.rushhour.domain.account.dto.LoginRequest;
import com.prime.rushhour.domain.account.dto.LoginResponse;
import com.prime.rushhour.domain.account.service.AccountService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountService accountService;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(AccountService accountService, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var account = accountService.findByEmail(username);
        return new CustomUserDetails(account);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        CustomUserDetails account = (CustomUserDetails) loadUserByUsername(loginRequest.username());
        if (!passwordEncoder.matches(loginRequest.password(), account.getPassword())) {
            throw new RuntimeException("Wrong Email or Password");
        }

        var token = jwtService.generateToken(account);
        return new LoginResponse(token);
    }
}
