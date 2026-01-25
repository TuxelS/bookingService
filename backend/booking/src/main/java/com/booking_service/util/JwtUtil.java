package com.booking_service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access_token.lifetime}")
    private Duration tokenAccessLifetime;

    private SecretKey getSingingKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_USER");
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + tokenAccessLifetime.toMillis());
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(issuedDate)
                .expiration(expiredDate)
                .signWith(getSingingKey())
                .compact();
    }

    private Claims getAllClaimsFromAccessToken(String accessToken) {
        return Jwts.parser()
                .verifyWith(getSingingKey())
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();
    }

    public String getEmailFromAccessToken(String accessToken) {
        return getAllClaimsFromAccessToken(accessToken).getSubject();
    }
}
