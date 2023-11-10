package com.example.plathome.user_report.domain;

import com.example.plathome.global.domain.AuditingFields;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_report_id")
    private Long id;

    private Long reportUserId;

    private Long targetUserId;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String context;

    @Embedded
    private AuditingFields auditingFields;

    @Builder
    public UserReport(Long reportUserId, Long targetUserId, String context) {
        this.reportUserId = reportUserId;
        this.targetUserId = targetUserId;
        this.context = context;
    }
}
