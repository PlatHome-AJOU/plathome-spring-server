package com.example.plathome.login.jwt.service.redis;

import com.example.plathome.global.service.RedisService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static com.example.plathome.login.jwt.common.JwtStaticField.REFRESH_TOKEN_EXPIRATION;

@Component
public class RefreshTokenRedisService implements RedisService {
    private final RedisTemplate<String, String> refreshTokenRedisTemplate;

    public RefreshTokenRedisService(
            @Qualifier("RefreshTokenRedisTemplate") RedisTemplate<String, String> refreshTokenRedisTemplate) {
        this.refreshTokenRedisTemplate = refreshTokenRedisTemplate;
    }

    @Override
    public void setData(String userId, String refreshToken) {
        refreshTokenRedisTemplate.opsForValue().set(userId, refreshToken, REFRESH_TOKEN_EXPIRATION, TimeUnit.MILLISECONDS);
    }

    @Transactional(readOnly = true)
    @Override
    public String getData(String userId) {
        return refreshTokenRedisTemplate.opsForValue().get(userId);
    }

    @Override
    public void deleteData(String userId) {
        refreshTokenRedisTemplate.delete(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean checkExistValue(String userId) {
        return refreshTokenRedisTemplate.hasKey(userId);
    }
}
