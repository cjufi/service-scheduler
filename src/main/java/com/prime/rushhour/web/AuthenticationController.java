package com.prime.rushhour.web;

import com.prime.rushhour.domain.account.dto.LoginRequest;
import com.prime.rushhour.domain.account.dto.LoginResponse;
import com.prime.rushhour.infrastructure.security.CustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final CustomUserDetailsService customUserDetailsService;

    public AuthenticationController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(customUserDetailsService.login(loginRequest), HttpStatus.OK);
    }
}