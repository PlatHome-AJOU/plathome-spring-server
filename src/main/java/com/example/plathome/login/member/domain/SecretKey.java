package com.example.plathome.login.member.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public record SecretKey(@Value("${jwt.secretKey}") String value) {
    public byte[] getDecoded() {
        return Base64.getDecoder().decode(value);
    }
}