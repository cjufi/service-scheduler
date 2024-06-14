package com.prime.rushhour.web;

import com.prime.rushhour.domain.account.dto.LoginRequest;
import com.prime.rushhour.domain.account.dto.LoginResponse;
import com.prime.rushhour.infrastructure.security.CustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing authentication.
 *
 * Provides an endpoint for user login.
 *
 * @version 1.0
 * @author Filip
 */
@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Constructs a new AuthenticationController with the specified CustomUserDetailsService.
     *
     * @param customUserDetailsService the service for managing user details and authentication
     */
    public AuthenticationController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Authenticates a user and returns a login response with authentication details.
     *
     * @param loginRequest the request body containing login details
     * @return the login response with authentication details
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(customUserDetailsService.login(loginRequest), HttpStatus.OK);
    }
}
