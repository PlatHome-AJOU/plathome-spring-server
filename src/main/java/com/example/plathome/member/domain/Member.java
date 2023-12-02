package com.example.plathome.member.domain;

import com.example.plathome.global.domain.AuditingFields;
import com.example.plathome.member.domain.types.RoleType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(columnList = "email", unique = true),
        @Index(columnList = "nickname", unique = true)
})
@Entity
public class Member extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 20)
    private String nickname;

    @Column (length = 100)
    private String email;

    @Column(length = 60)
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Builder
    private Member(Long id, String nickname, String email, String password, RoleType roleType, String createdBy, String modifiedBy) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.roleType = roleType;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }

    public static MemberBuilder of() {
        return Member.builder();
    }
}
