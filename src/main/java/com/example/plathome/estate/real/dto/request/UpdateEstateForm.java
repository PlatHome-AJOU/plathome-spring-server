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
        @NotNull @DecimalMin("0.0") @Digits(integer=3, fraction=2) Double squareFeet,
        @NotNull @Min(0) Integer deposit,
        @NotNull @Min(0) Integer maintenanceFee,
        @NotNull @Min(0) Integer monthlyRent
) {
    public static UpdateEstateFormBuilder of() {
        return UpdateEstateForm.builder();
    }
}
