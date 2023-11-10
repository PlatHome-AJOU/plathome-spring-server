package com.example.plathome.login.member.dto;

import com.example.plathome.member.domain.Member;
import lombok.Builder;

import static com.example.plathome.login.member.common.JwtStaticField.BEARER;

@Builder
public record MemberWithTokenDto(
        Long id,
        String username,
        String userId,
        String refreshToken
){

    public static MemberWithTokenDtoBuilder of(){
        return MemberWithTokenDto.builder();
    }

    public static MemberWithTokenDto from(Member entity, String refreshToken) {
        return MemberWithTokenDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .userId(entity.getUserId())
                .refreshToken(BEARER + refreshToken).build();
    }

    public static MemberWithTokenDto withoutToken(Member entity) {
        return MemberWithTokenDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .userId(entity.getUserId()).build();
    }
}
