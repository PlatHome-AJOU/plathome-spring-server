package com.example.plathome.login.member.dto.response;

import com.example.plathome.login.member.dto.MemberWithTokenDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MemberWithTokenResponse(
        String username,
        String userId,
        String refreshToken
) {

    public static MemberWithTokenResponseBuilder of() {
        return MemberWithTokenResponse.builder();
    }

    public static MemberWithTokenResponse from(MemberWithTokenDto dto) {
        return MemberWithTokenResponse.builder()
                .username(dto.username())
                .userId(dto.userId())
                .refreshToken(dto.refreshToken()).build();
    }

    public static MemberWithTokenResponse withoutToken(MemberWithTokenDto dto) {
        return MemberWithTokenResponse.builder()
                .username(dto.username())
                .userId(dto.userId()).build();
    }
}
