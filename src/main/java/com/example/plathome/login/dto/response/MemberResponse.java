package com.example.plathome.login.dto.response;

import com.example.plathome.member.domain.Member;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MemberResponse(
        long id,
        String nickname,
        String email
) {
    public static MemberResponseBuilder of() {
        return MemberResponse.builder();
    }

    public static MemberResponse from(Member entity) {
        return MemberResponse.builder()
                .id(entity.getId())
                .nickname(entity.getNickname())
                .email(entity.getEmail())
                .build();
    }
}
