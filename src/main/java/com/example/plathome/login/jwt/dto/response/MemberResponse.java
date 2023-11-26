package com.example.plathome.login.jwt.dto.response;

import com.example.plathome.login.jwt.dto.MemberWithTokenDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MemberResponse(
        String nickname,
        String email
) {
    public static MemberResponseBuilder of() {
        return MemberResponse.builder();
    }

    public static MemberResponse from(MemberWithTokenDto dto) {
        return MemberResponse.builder()
                .nickname(dto.nickname())
                .email(dto.email()).build();
    }
}
