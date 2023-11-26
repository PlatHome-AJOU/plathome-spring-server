package com.example.plathome.estate_report.dto.response;

import com.example.plathome.estate_report.domain.EstateReport;
import lombok.Builder;

@Builder
public record EstateReportResponse(
        Long memberId,
        Long estateId,
        String context
) {
    public static EstateReportResponseBuilder of() {
        return EstateReportResponse.builder();
    }

    public static EstateReportResponse from(EstateReport entity) {
        return EstateReportResponse.of()
                .memberId(entity.getMemberId())
                .estateId(entity.getEstateId())
                .context(entity.getContext()).build();
    }
}
