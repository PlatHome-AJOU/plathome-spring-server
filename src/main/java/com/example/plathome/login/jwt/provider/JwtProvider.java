package com.example.plathome.login.jwt.provider;

import com.example.plathome.login.jwt.domain.SecretKey;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

import static com.example.plathome.login.jwt.common.JwtStaticField.*;


@RequiredArgsConstructor
@Service
public class JwtProvider {

    private final SecretKey secretKey;

    public String createAccessToken(String userId) {
        byte[] decodedSecretKey = secretKey.getDecoded();
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(decodedSecretKey))
                .compact();
    }

    public String createRefreshToken(String userId) {
        byte[] decodedSecretKey = secretKey.getDecoded();
        return Jwts.builder()
                .setSubject(userId)
                .setId(UUID.randomUUID().toString()) // Unique ID for refresh token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(decodedSecretKey))
                .compact();
    }
}
