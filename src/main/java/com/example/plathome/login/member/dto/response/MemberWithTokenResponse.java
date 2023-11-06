package com.example.plathome.login.member.dto.response;

import com.example.plathome.login.member.dto.MemberWithTokenDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MemberWithTokenResponse(
        String username,
        String userId,
        String refreshToken
) {

    @Builder
    public MemberWithTokenResponse {
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
