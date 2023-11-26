package com.example.plathome.login.jwt.dto;

import com.example.plathome.member.domain.Member;
import lombok.Builder;

import static com.example.plathome.login.jwt.common.JwtStaticField.BEARER;

@Builder
public record MemberWithTokenDto(
        long id,
        String nickname,
        String email,
        String accessToken,
        String refreshToken
){

    public static MemberWithTokenDtoBuilder of(){
        return MemberWithTokenDto.builder();
    }

    public static MemberWithTokenDto from(Member entity, String accessToken, String refreshToken) {
        return MemberWithTokenDto.builder()
                .id(entity.getId())
                .nickname(entity.getNickname())
                .email(entity.getEmail())
                .accessToken(BEARER + accessToken)
                .refreshToken(BEARER + refreshToken).build();
    }

    public static MemberWithTokenDto withoutToken(Member entity) {
        return MemberWithTokenDto.builder()
                .id(entity.getId())
                .nickname(entity.getNickname())
                .email(entity.getEmail()).build();
    }
}
