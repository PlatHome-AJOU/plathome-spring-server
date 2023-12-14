package com.example.plathome.login.provider;

import com.example.plathome.login.domain.AccessSecretKey;
import com.example.plathome.login.domain.RefreshSecretKey;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

import static com.example.plathome.global.constant.JwtStaticField.*;


@RequiredArgsConstructor
@Service
public class JwtProvider {

    private final AccessSecretKey accessSecretKey;
    private final RefreshSecretKey refreshSecretKey;

    public String createAccessToken(String memberId) {
        byte[] decodedSecretKey = accessSecretKey.getDecoded();
        return Jwts.builder()
                .setSubject(memberId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(decodedSecretKey))
                .compact();
    }

    public String createRefreshToken(String memberId) {
        byte[] decodedSecretKey = refreshSecretKey.getDecoded();
        return Jwts.builder()
                .setSubject(memberId)
                .setId(UUID.randomUUID().toString()) // Unique ID for refresh token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(decodedSecretKey))
                .compact();
    }
}
