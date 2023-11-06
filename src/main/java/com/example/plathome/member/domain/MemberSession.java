package com.example.plathome.member.domain;

import lombok.Builder;

public record MemberSession(
        Long id,
        String username,
        String userId,
        String password
        ){

    public static final String MEMBER_SESSION = "MemberSession";

    @Builder
    public static MemberSession of(Long id, String username, String userId, String password) {
        return new MemberSession(id, username, userId, password);
    }

    public static MemberSession from(Member member) {
        return MemberSession.builder()
                .id(member.getId())
                .username(member.getUsername())
                .userId(member.getUserId())
                .password(member.getPassword()).build();
    }


}
