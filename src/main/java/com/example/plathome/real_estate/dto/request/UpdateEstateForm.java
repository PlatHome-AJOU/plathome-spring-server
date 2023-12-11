package com.example.plathome.real_estate.dto.request;

import com.example.plathome.global.domain.estate.common.Option;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UpdateEstateForm(
        @NotNull Long estateId,
        @NotBlank @Size(max = 16777215) String context,
        @NotNull LocalDate contractTerm,
        Option option,
        @NotNull @DecimalMin("0.0") @Digits(integer=3, fraction=2) Double squareFeet,
        @NotNull @Min(0) Integer deposit,
        @NotNull @Min(0) Integer maintenanceFee,
        @NotNull @Min(0) Integer monthlyRent
) {
    public static UpdateEstateFormBuilder of() {
        return UpdateEstateForm.builder();
    }
}
