package com.example.plathome.login.jwt.dto.response;

import com.example.plathome.login.jwt.dto.MemberWithTokenDto;
import lombok.Builder;

@Builder
public record TokenResponse (
        String accessToken,
        String refreshToken
){
    public static TokenResponseBuilder of() {
        return TokenResponse.builder();
    }

    public static TokenResponse from(MemberWithTokenDto dto) {
        return TokenResponse.of()
                .accessToken(dto.accessToken())
                .refreshToken(dto.refreshToken()).build();
    }
}
