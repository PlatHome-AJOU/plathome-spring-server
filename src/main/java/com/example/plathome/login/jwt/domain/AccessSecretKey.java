package com.example.plathome.login.jwt.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public record AccessSecretKey(@Value("${jwt.access.secret-key}") String value) {

    public static final String ACCESS_KEY_PATH = "jwt.access.secret-key";
    public byte[] getDecoded() {
        return Base64.getDecoder().decode(value);
    }
}