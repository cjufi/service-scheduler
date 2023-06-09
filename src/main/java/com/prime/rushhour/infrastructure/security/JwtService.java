package com.prime.rushhour.infrastructure.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtService.class);

    @Value("${app.jwt.secret-key}")
    private String secretKey;

    @Value("${app.jwt.expiration}")
    private Long expiration;

    public String generateToken(UserDetails userDetails) {

        Date creationTime = new Date();
        Date expirationTime = new Date(creationTime.getTime() + expiration);

        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
        String roleName = customUserDetails.getAccount().getRole().getName();

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", roleName);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(creationTime)
                .setExpiration(expirationTime)
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String extractUsername(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String jwt) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwt);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException |
                 IllegalArgumentException ex) {
            LOGGER.error("Invalid jwt token");
        }
        return false;
    }
}