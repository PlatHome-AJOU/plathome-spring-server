package com.example.plathome.member.domain;

import lombok.Builder;

@Builder
public record MemberSession(
        Long id,
        String username,
        String userId,
        String password
        ){

    public static final String MEMBER_SESSION = "MemberSession";

    public static MemberSessionBuilder of() {
        return MemberSession.builder();
    }

    public static MemberSession from(Member member) {
        return MemberSession.builder()
                .id(member.getId())
                .username(member.getUsername())
                .userId(member.getUserId())
                .password(member.getPassword()).build();
    }


}
