package com.example.plathome.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableRedisRepositories
@RequiredArgsConstructor
@Configuration
public class RedisConfig {
    //Redis서버와 연결 정보를 저장하는 객체
    private final RedisProperties redisProperties;

    // RedisProperties로 yaml에 저장한 host, post를 연결
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
    }

    @Bean
    public RedisTemplate<String, String> RefreshTokenRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        LettuceConnectionFactory lettuceConnectionFactory1 = new LettuceConnectionFactory();
        lettuceConnectionFactory1.setDatabase(0);
        lettuceConnectionFactory1.afterPropertiesSet();
        return createRedisTemplate(lettuceConnectionFactory1);
    }

    @Bean
    public RedisTemplate<String, String> AuthCodeRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        LettuceConnectionFactory lettuceConnectionFactory2 = new LettuceConnectionFactory();
        lettuceConnectionFactory2.setDatabase(1);
        lettuceConnectionFactory2.afterPropertiesSet();
        return createRedisTemplate(lettuceConnectionFactory2);
    }

    // serializer 설정으로 redis-cli를 통해 직접 데이터를 조회할 수 있도록 설정
    private RedisTemplate<String, String> createRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
