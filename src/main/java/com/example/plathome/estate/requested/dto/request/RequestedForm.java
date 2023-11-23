package com.example.plathome.estate.requested.dto.request;

import com.example.plathome.estate.Option;
import com.example.plathome.estate.requested.domain.Requested;
import com.example.plathome.estate.constant.RentalType;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;

@Builder
public record RequestedForm(
        @NotBlank @Size(max = 255) String location,
        @NotNull RentalType rentalType,
        @NotNull LocalDate contractTerm,
        Set<Option> options,
        @NotNull @DecimalMin("0.0") @Digits(integer=3, fraction=2) double squareFeet,
        @NotNull @Min(0) int maintenanceFee,
        @NotNull @Min(0) int monthlyRent
) {

    public static RequestedFormBuilder of(){
        return RequestedForm.builder();
    }

    public Requested toEntity(String userId, String url) {
        return Requested.builder()
                .userId(userId)
                .location(this.location)
                .rentalType(this.rentalType)
                .contractUrl(url)
                .contractTerm(this.contractTerm)
                .options(this.options)
                .squareFeet(this.squareFeet)
                .maintenanceFee(this.maintenanceFee)
                .monthlyRent(this.monthlyRent).build();
    }
}
