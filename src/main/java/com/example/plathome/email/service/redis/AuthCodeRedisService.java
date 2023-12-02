package com.example.plathome.email.service.redis;

import com.example.plathome.email.constant.EmailStaticField;
import com.example.plathome.global.service.RedisService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Component
public class AuthCodeRedisService implements RedisService {
    private final RedisTemplate<String, String> authCodeRedisTemplate;

    public AuthCodeRedisService(
            @Qualifier("AuthCodeRedisTemplate") RedisTemplate<String, String> authCodeRedisTemplate) {
        this.authCodeRedisTemplate = authCodeRedisTemplate;
    }

    @Override
    public void setData(String email, String authCode) {
        authCodeRedisTemplate.opsForValue().set(email, authCode, EmailStaticField.AUTH_CODE_EXPIRATION, TimeUnit.MILLISECONDS);
    }

    @Transactional(readOnly = true)
    @Override
    public String getData(String email) {
        return authCodeRedisTemplate.opsForValue().get(email);
    }

    @Override
    public void deleteData(String email) {
        authCodeRedisTemplate.delete(email);
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean checkExistValue(String email) {
        return authCodeRedisTemplate.hasKey(email);
    }
}
