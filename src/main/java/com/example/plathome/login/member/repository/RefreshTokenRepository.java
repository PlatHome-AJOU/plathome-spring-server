package com.example.plathome.login.member.repository;

import com.example.plathome.login.member.domain.RefreshToken;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Getter
@Repository
public class RefreshTokenRepository {

    Map<String, Set<RefreshToken>> validRefreshTokens = new HashMap<>();

    public String save(String userId, String refreshToken) {
        Set<RefreshToken> refreshTokens = validRefreshTokens.getOrDefault(userId, new HashSet<>());
        refreshTokens.add(RefreshToken.of(refreshToken));
        validRefreshTokens.put(userId, refreshTokens);
        return refreshToken;
    }

    public void invalidate(String userId) {
        validRefreshTokens.remove(userId);
    }

    public void clear() {
        validRefreshTokens.clear();
    }
}
