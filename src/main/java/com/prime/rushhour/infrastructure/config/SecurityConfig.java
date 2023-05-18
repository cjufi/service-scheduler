package com.prime.rushhour.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/v1/client/login",
                        "/api/v1/employee/login"
                ).permitAll()
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
                .httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .build();
    }
}