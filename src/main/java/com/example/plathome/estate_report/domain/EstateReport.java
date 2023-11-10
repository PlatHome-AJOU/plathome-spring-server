package com.example.plathome.estate_report.domain;

import com.example.plathome.global.domain.AuditingFields;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class EstateReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estate_report_id")
    private Long id;

    private Long reportUserId;

    private Long targetEstateId;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String context;

    @Embedded
    private AuditingFields auditingFields;

    @Builder
    public EstateReport(Long reportUserId, Long targetEstateId, String context, AuditingFields auditingFields) {
        this.reportUserId = reportUserId;
        this.targetEstateId = targetEstateId;
        this.context = context;
        this.auditingFields = auditingFields;
    }
}
