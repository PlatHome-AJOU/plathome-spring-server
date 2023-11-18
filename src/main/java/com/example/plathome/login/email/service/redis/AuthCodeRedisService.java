package com.example.plathome.login.email.service.redis;

import com.example.plathome.global.service.RedisService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static com.example.plathome.login.email.common.EmailStaticField.AUTH_CODE_EXPIRATION;
import static com.example.plathome.login.jwt.common.JwtStaticField.REFRESH_TOKEN_EXPIRATION;

@Component
public class AuthCodeRedisService implements RedisService {
    private final RedisTemplate<String, String> authCodeRedisTemplate;

    public AuthCodeRedisService(
            @Qualifier("AuthCodeRedisTemplate") RedisTemplate<String, String> authCodeRedisTemplate) {
        this.authCodeRedisTemplate = authCodeRedisTemplate;
    }

    @Override
    public void setData(String userId, String authCode) {
        authCodeRedisTemplate.opsForValue().set(userId, authCode, AUTH_CODE_EXPIRATION, TimeUnit.MILLISECONDS);
    }

    @Transactional(readOnly = true)
    @Override
    public String getData(String userId) {
        return authCodeRedisTemplate.opsForValue().get(userId);
    }

    @Override
    public void deleteData(String userId) {
        authCodeRedisTemplate.delete(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean checkExistValue(String userId) {
        return authCodeRedisTemplate.hasKey(userId);
    }
}
