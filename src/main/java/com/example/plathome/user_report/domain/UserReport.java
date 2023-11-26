package com.example.plathome.user_report.domain;

import com.example.plathome.global.domain.AuditingFields;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserReport extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_report_id")
    private Long id;

    private Long reportMemberId;

    private Long targetMemberId;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String context;

    @Builder
    public UserReport(Long reportMemberId, Long targetMemberId, String context) {
        this.reportMemberId = reportMemberId;
        this.targetMemberId = targetMemberId;
        this.context = context;
    }
}
