package com.example.plathome.estate_report.dto.request;

import com.example.plathome.estate_report.domain.EstateReport;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record EstateReportForm(
        @NotNull Long estateId,
        @NotBlank @Size(max = 16777215) String context  // MEDIUMTEXT의 최대 길이는 약 16MB
) {
    public static EstateReportFormBuilder of() {
        return EstateReportForm.builder();
    }

    public EstateReport toEntity(long memberId) {
        return EstateReport.builder()
                .memberId(memberId)
                .estateId(this.estateId())
                .context(this.context()).build();
    }
}
