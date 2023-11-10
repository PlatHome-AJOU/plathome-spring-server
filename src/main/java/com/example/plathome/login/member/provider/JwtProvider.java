package com.example.plathome.login.member.provider;

import com.example.plathome.login.member.domain.SecretKey;
import com.example.plathome.login.member.repository.RefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

import static com.example.plathome.login.member.common.JwtStaticField.BEARER;


@RequiredArgsConstructor
@Service
public class JwtProvider {

    private final SecretKey secretKey;

    private final RefreshTokenRepository refreshTokenRepository;
    private final long accessTokenExpiration = 1000L * 60 * 15; // Access token is 15 minutes.
    private final long refreshTokenExpiration = 1000L * 60 * 60 * 24; // Refresh token is one day.

    public String createAccessToken(String userId) {
        byte[] decodedSecretKey = secretKey.getDecoded();
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(Keys.hmacShaKeyFor(decodedSecretKey))
                .compact();
    }

    public String createRefreshToken(String userId) {
        byte[] decodedSecretKey = secretKey.getDecoded();
        String refreshToken = Jwts.builder()
                .setSubject(userId)
                .setId(UUID.randomUUID().toString()) // Unique ID for refresh token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(Keys.hmacShaKeyFor(decodedSecretKey))
                .compact();

        return refreshTokenRepository.save(userId, refreshToken);
    }

    public void setAuthorizationHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(HttpHeaders.AUTHORIZATION, BEARER + accessToken);
    }

    public void expiredRefreshToken(String userId) {
        refreshTokenRepository.invalidate(userId);
    }
}