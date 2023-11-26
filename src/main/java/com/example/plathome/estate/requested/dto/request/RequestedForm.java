package com.example.plathome.estate.requested.dto.request;

import com.example.plathome.estate.common.Option;
import com.example.plathome.estate.common.Floor;
import com.example.plathome.estate.common.RoomType;
import com.example.plathome.estate.requested.domain.Requested;
import com.example.plathome.estate.common.RentalType;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record RequestedForm(
        @NotBlank @Size(max = 255) String location,
        @NotNull RoomType roomType,
        @NotNull RentalType rentalType,
        @NotNull Floor floor,
        @NotNull LocalDate contractTerm,
        @NotNull Option option,
        @NotNull @DecimalMin("0.0") @Digits(integer=3, fraction=2) double squareFeet,
        @NotNull @Min(0) int deposit,
        @NotNull @Min(0) int maintenanceFee,
        @NotNull @Min(0) int monthlyRent
) {

    public static RequestedFormBuilder of(){
        return RequestedForm.builder();
    }

    public Requested toEntity(long memberId, String url) {
        return Requested.builder()
                .memberId(memberId)
                .location(this.location())
                .roomType(this.roomType())
                .rentalType(this.rentalType())
                .floor(this.floor())
                .contractUrl(url)
                .contractTerm(this.contractTerm())
                .option(this.option())
                .squareFeet(this.squareFeet())
                .deposit(this.deposit())
                .maintenanceFee(this.maintenanceFee())
                .monthlyRent(this.monthlyRent()).build();
    }
}
