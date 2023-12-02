package com.example.plathome.login.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public record RefreshSecretKey(@Value("${jwt.refresh.secret-key}") String value) {

    public static final String REFRESH_KEY_PATH = "jwt.refresh.secret-key";
    public byte[] getDecoded() {
        return Base64.getDecoder().decode(value);
    }
}