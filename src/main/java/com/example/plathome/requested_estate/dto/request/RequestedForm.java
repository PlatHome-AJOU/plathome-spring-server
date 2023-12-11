package com.example.plathome.requested_estate.dto.request;

import com.example.plathome.global.domain.estate.common.Option;
import com.example.plathome.global.domain.estate.common.Floor;
import com.example.plathome.global.domain.estate.common.RoomType;
import com.example.plathome.requested_estate.domain.Requested;
import com.example.plathome.global.domain.estate.common.RentalType;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record RequestedForm(
        @NotBlank @Size(max = 255) String location,
        @NotBlank @Size(max = 16777215) String context,
        @NotNull RoomType roomType,
        @NotNull RentalType rentalType,
        @NotNull Floor floor,
        @NotNull LocalDate contractTerm,
        @NotNull Option option,
        @NotNull @DecimalMin("0.0") @Digits(integer=3, fraction=2) Double squareFeet,
        @NotNull @Min(0) Integer deposit,
        @NotNull @Min(0) Integer maintenanceFee,
        @NotNull @Min(0) Integer monthlyRent
) {

    public static RequestedFormBuilder of(){
        return RequestedForm.builder();
    }

    public Requested toEntity(long memberId, String url) {
        return Requested.builder()
                .memberId(memberId)
                .location(this.location())
                .context(this.context())
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
