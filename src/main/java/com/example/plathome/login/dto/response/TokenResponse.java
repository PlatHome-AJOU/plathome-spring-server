package com.example.plathome.login.dto.response;

import lombok.Builder;

import static com.example.plathome.global.constant.JwtStaticField.BEARER;

@Builder
public record TokenResponse (
        String accessToken,
        String refreshToken
){
    public static TokenResponseBuilder of() {
        return TokenResponse.builder();
    }

    public static TokenResponse from(String accessToken, String refreshToken) {
        return TokenResponse.of()
                .accessToken(BEARER + accessToken)
                .refreshToken(BEARER + refreshToken).build();
    }
}
