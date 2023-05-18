package com.prime.rushhour.infrastructure.config;

import com.prime.rushhour.infrastructure.security.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/authenticate/login").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/v1/client").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/employee").hasAnyAuthority("SCOPE_ADMIN", "SCOPE_PROVIDER_ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/provider").hasAuthority("SCOPE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/client/{id}").hasAuthority("SCOPE_CLIENT")
                .requestMatchers(HttpMethod.PUT, "/api/v1/employee/{id}").hasAnyAuthority("SCOPE_ADMIN", "SCOPE_PROVIDER_ADMIN", "SCOPE_EMPLOYEE")
                .requestMatchers(HttpMethod.PUT, "/api/v1/provider/{id}").hasAnyAuthority("SCOPE_ADMIN", "SCOPE_PROVIDER_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/employee/{id}").hasAnyAuthority("SCOPE_ADMIN", "SCOPE_PROVIDER_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/client/{id}").hasAnyAuthority("SCOPE_ADMIN", "SCOPE_CLIENT")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/provider/{id}").hasAuthority("SCOPE_ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/provider").hasAnyAuthority("SCOPE_ADMIN", "SCOPE_CLIENT")
                .requestMatchers(HttpMethod.GET, "/api/v1/provider/{id}").hasAnyAuthority("SCOPE_ADMIN", "SCOPE_CLIENT")
                .requestMatchers(HttpMethod.GET, "/api/v1/client").hasAuthority("SCOPE_ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/client/{id}").hasAnyAuthority("SCOPE_ADMIN", "SCOPE_CLIENT")
                .requestMatchers(HttpMethod.GET, "/api/v1/employee").hasAnyAuthority("SCOPE_ADMIN", "SCOPE_PROVIDER_ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/employee/{id}").hasAnyAuthority("SCOPE_ADMIN", "SCOPE_PROVIDER_ADMIN", "SCOPE_EMPLOYEE")
                .anyRequest().authenticated()
                .and().sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}