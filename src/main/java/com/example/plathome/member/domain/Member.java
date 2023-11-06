package com.example.plathome.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {@Index(columnList = "userId", unique = true)})
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String userId;
    private String password;

    @Builder
    private Member(Long id, String username, String userId, String password) {
        this.id = id;
        this.username = username;
        this.userId = userId;
        this.password = password;
    }

    @Builder
    public static Member of(String username, String userId, String password) {
        return Member.builder()
                .username(username)
                .userId(userId)
                .password(password).build();
    }
}
