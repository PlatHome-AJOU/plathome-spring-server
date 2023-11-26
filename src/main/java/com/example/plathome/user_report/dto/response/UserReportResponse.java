package com.example.plathome.user_report.dto.response;

import com.example.plathome.user_report.domain.UserReport;
import lombok.Builder;

@Builder
public record UserReportResponse(
        long reportMemberId,
        long targetMemberId,
        String context
) {
    public static UserReportResponseBuilder of() {
        return UserReportResponse.builder();
    }

    public static UserReportResponse from(UserReport entity) {
        return UserReportResponse.of()
                .reportMemberId(entity.getReportMemberId())
                .targetMemberId(entity.getTargetMemberId())
                .context(entity.getContext()).build();
    }
}
