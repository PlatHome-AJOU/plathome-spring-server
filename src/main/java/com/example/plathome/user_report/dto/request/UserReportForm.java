package com.example.plathome.user_report.dto.request;

import com.example.plathome.user_report.domain.UserReport;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserReportForm (
        @NotNull long targetMemberId,
        @NotBlank @Size(max = 16777215) String context  // MEDIUMTEXT의 최대 길이는 약 16MB
){
    public static UserReportFormBuilder of() {
        return UserReportForm.builder();
    }

    public UserReport toEntity(long reportMemberId) {
        return UserReport.builder()
                .reportMemberId(reportMemberId)
                .targetMemberId(this.targetMemberId())
                .context(this.context()).build();
    }
}
