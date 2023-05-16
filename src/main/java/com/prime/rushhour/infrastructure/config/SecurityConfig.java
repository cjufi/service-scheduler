package com.prime.rushhour.infrastructure.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.prime.rushhour.domain.account.repository.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final RsaKeyProperties rsaKeys;

    private final AccountRepository accountRepository;

    public SecurityConfig(RsaKeyProperties rsaKeys, AccountRepository accountRepository) {
        this.rsaKeys = rsaKeys;
        this.accountRepository = accountRepository;
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
                .requestMatchers(HttpMethod.GET, "/api/v1/client").hasAnyAuthority("SCOPE_ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/employee").hasAnyAuthority("SCOPE_ADMIN", "SCOPE_PROVIDER_ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/employee/{id}").hasAnyAuthority("SCOPE_ADMIN", "SCOPE_PROVIDER_ADMIN", "SCOPE_EMPLOYEE")
                .anyRequest().authenticated()
                .and().sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService() {
        return email -> {
            var account = accountRepository.findByEmail(email);
            if(account == null) {
                throw new RuntimeException("Account not found");
            }
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(account.getRole().getName()));
            return new CustomUserDetails(account.getEmail(), account.getPassword(), account.getId(), authorities);
        };
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder()).and().build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
}