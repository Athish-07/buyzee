package com.buyzee.user_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Service
public class JwtService {
    private final Key key;
    private final long expirationMinutes;

    public JwtService(
            @Value("${buyzee.security.jwt.secret}")
            String secret,

            @Value("${buyzee.security.jwt.exp-min:60}")
            long expirationMinutes
            )
    {
        this.key= Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMinutes=expirationMinutes;
    }

    public String generateToken(String subject, Map<String, Object> claims){
        Instant now =Instant.now();
        return Jwts.builder()
                .setSubject(subject)
                .setClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(expirationMinutes*60)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
