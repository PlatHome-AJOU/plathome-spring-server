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
public class EstateReport extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estate_report_id")
    private Long id;

    private Long memberId;

    private Long estateId;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String context;

    @Builder
    public EstateReport(Long memberId, Long estateId, String context) {
        this.memberId = memberId;
        this.estateId = estateId;
        this.context = context;
    }
}
