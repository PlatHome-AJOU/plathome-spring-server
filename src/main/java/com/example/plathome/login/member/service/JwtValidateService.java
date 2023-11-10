package com.example.plathome.login.member.service;


import com.example.plathome.login.member.domain.RefreshToken;
import com.example.plathome.login.member.domain.SecretKey;
import com.example.plathome.login.member.exception.ExpiredAccessTokenException;
import com.example.plathome.login.member.exception.ExpiredRefreshTokenException;
import com.example.plathome.login.member.exception.InvalidAccessTokenException;
import com.example.plathome.login.member.exception.InvalidRefreshTokenException;
import com.example.plathome.login.member.provider.JwtProvider;
import com.example.plathome.login.member.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

import static com.example.plathome.login.member.common.JwtStaticField.BEARER;


@RequiredArgsConstructor
@Service
public class JwtValidateService {

    private final SecretKey secretKey;
    private final RefreshTokenRepository refreshTokenRepository;

    public Claims validateAccessToken(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            String token = authHeader.substring(BEARER.length());
            return this.validateToken(token);
        } catch (ExpiredJwtException e) {
            throw new ExpiredAccessTokenException();
        } catch (Exception e) {
            throw new InvalidAccessTokenException();
        }
    }

    public Claims validateRefreshToken(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            String token = authHeader.substring(BEARER.length());
            this.verifyValidRefreshToken(token);
            return this.validateToken(token);
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

    private void verifyValidRefreshToken(String refreshToken) {
        Map<String, Set<RefreshToken>> validRefreshTokens = refreshTokenRepository.getValidRefreshTokens();
        if (validRefreshTokens.values().stream().noneMatch(set -> set.contains(RefreshToken.of(refreshToken)))) {
            throw new InvalidRefreshTokenException();
        }
    }
}
