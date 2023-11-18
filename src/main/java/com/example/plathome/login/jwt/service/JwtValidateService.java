package com.example.plathome.login.jwt.service;


import com.example.plathome.login.jwt.domain.SecretKey;
import com.example.plathome.login.jwt.exception.*;
import com.example.plathome.login.jwt.service.redis.RefreshTokenRedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import static com.example.plathome.login.jwt.common.JwtStaticField.BEARER;


@RequiredArgsConstructor
@Service
public class JwtValidateService {

    private final SecretKey secretKey;
    private final RefreshTokenRedisService refreshTokenRedisService;

    public String validateAccessToken(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            String token = authHeader.substring(BEARER.length());
            Claims claims = this.validateToken(token);
            String userId = claims.getSubject();
            this.validateIsNotInRedisRefreshToken(userId, token);
            return userId;
        } catch (ExpiredJwtException e) {
            throw new ExpiredAccessTokenException();
        } catch (Exception e) {
            throw new InvalidAccessTokenException();
        }
    }

    public String validateRefreshToken(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            String token = authHeader.substring(BEARER.length());
            Claims claims = this.validateToken(token);
            String userId = claims.getSubject();
            this.validateIsInRedisRefreshToken(userId, token);
            return userId;
        } catch (ExpiredJwtException e) {
            throw new ExpiredRefreshTokenException();
        } catch (Exception e) {
            throw new InvalidRefreshTokenException();
        }
    }

    private Claims validateToken(String token) {
        byte[] decodedSecretKey = secretKey.getDecoded();
        javax.crypto.SecretKey key = Keys.hmacShaKeyFor(decodedSecretKey);

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private void validateIsNotInRedisRefreshToken(String userId, String token) {
        String data = refreshTokenRedisService.getData(userId);
        if (data == null) {
            throw new ExpiredRefreshTokenException();
        } else if (data.equals(token)) {
            throw new InvalidAccessTokenException();
        }
    }

    private void validateIsInRedisRefreshToken(String userId, String token) {
        if (!refreshTokenRedisService.checkExistValue(userId)) {
            throw new ExpiredRefreshTokenException();
        } else if (!refreshTokenRedisService.getData(userId).equals(token)) {
            throw new InvalidRefreshTokenException();
        }
    }
}
