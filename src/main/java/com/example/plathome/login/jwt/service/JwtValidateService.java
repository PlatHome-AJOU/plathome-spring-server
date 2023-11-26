package com.example.plathome.login.jwt.service;


import com.example.plathome.login.jwt.domain.AccessSecretKey;
import com.example.plathome.login.jwt.domain.RefreshSecretKey;
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

import static com.example.plathome.login.jwt.common.JwtStaticField.*;


@RequiredArgsConstructor
@Service
public class JwtValidateService {

    private final AccessSecretKey accessSecretKey;
    private final RefreshSecretKey refreshSecretKey;
    private final RefreshTokenRedisService refreshTokenRedisService;

    public String validateAccessToken(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader(ACCESS_HEADER);
            String token = authHeader.substring(BEARER.length());
            Claims claims = this.validateToken(token, accessSecretKey.getDecoded());
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new ExpiredAccessTokenException();
        } catch (Exception e) {
            throw new InvalidAccessTokenException();
        }
    }

    public String validateRefreshToken(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader(REFRESH_HEADER);
            String token = authHeader.substring(BEARER.length());
            Claims claims = this.validateToken(token, refreshSecretKey.getDecoded());
            String memberId = claims.getSubject();
            this.validateIsInRedisRefreshToken(memberId, token);
            return memberId;
        } catch (ExpiredJwtException e) {
            throw new ExpiredRefreshTokenException();
        } catch (Exception e) {
            throw new InvalidRefreshTokenException();
        }
    }

    private Claims validateToken(String token, byte[] decodedSecretKey) {
        javax.crypto.SecretKey key = Keys.hmacShaKeyFor(decodedSecretKey);

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private void validateIsInRedisRefreshToken(String memberId, String token) {
        if (!refreshTokenRedisService.checkExistValue(memberId)) {
            throw new ExpiredRefreshTokenException();
        } else if (!refreshTokenRedisService.getData(memberId).equals(token)) {
            throw new InvalidRefreshTokenException();
        }
    }
}
