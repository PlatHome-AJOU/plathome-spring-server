package com.example.plathome.login.service.redis;

import com.example.plathome.global.service.RedisService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static com.example.plathome.global.constant.JwtStaticField.REFRESH_TOKEN_EXPIRATION;

@Component
public class RefreshTokenRedisService implements RedisService {
    private final RedisTemplate<String, String> refreshTokenRedisTemplate;

    public RefreshTokenRedisService(
            @Qualifier("RefreshTokenRedisTemplate") RedisTemplate<String, String> refreshTokenRedisTemplate) {
        this.refreshTokenRedisTemplate = refreshTokenRedisTemplate;
    }

    @Override
    public void setData(String stringMemberId, String refreshToken) {
        refreshTokenRedisTemplate.opsForValue().set(stringMemberId, refreshToken, REFRESH_TOKEN_EXPIRATION, TimeUnit.MILLISECONDS);
    }

    @Transactional(readOnly = true)
    @Override
    public String getData(String stringMemberId) {
        return refreshTokenRedisTemplate.opsForValue().get(stringMemberId);
    }

    @Override
    public void deleteData(String stringMemberId) {
        refreshTokenRedisTemplate.delete(stringMemberId);
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean checkExistValue(String stringMemberId) {
        return refreshTokenRedisTemplate.hasKey(stringMemberId);
    }
}
