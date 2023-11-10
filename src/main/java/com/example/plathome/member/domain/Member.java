package com.example.plathome.member.domain;

import com.example.plathome.global.domain.AuditingFields;
import com.example.plathome.member.domain.constant.RoleType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {@Index(columnList = "userId", unique = true)})
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 20)
    private String username;

    @Column (length = 100)
    private String userId;

    @Column(length = 60)
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Embedded
    private AuditingFields auditingFields;

    @Builder
    private Member(Long id, String username, String userId, String password, String createdBy, String modifiedBy) {
        this.id = id;
        this.username = username;
        this.userId = userId;
        this.password = password;
        this.roleType = RoleType.USER;
        this.auditingFields = new AuditingFields();
        this.auditingFields.setCreatedBy(createdBy);
        this.auditingFields.setModifiedBy(modifiedBy);
    }

    public static MemberBuilder of() {
        return Member.builder();
    }
}
