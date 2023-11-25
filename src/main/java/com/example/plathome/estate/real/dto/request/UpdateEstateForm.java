package com.example.plathome.estate.real.dto.request;

import com.example.plathome.estate.common.Option;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UpdateEstateForm(
        @NotNull LocalDate contractTerm,
        Option option,
        @NotNull @DecimalMin("0.0") @Digits(integer=3, fraction=2) double squareFeet,
        @NotNull @Min(0) int deposit,
        @NotNull @Min(0) int maintenanceFee,
        @NotNull @Min(0) int monthlyRent
) {
    public static UpdateEstateFormBuilder of() {
        return UpdateEstateForm.builder();
    }
}
