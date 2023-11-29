package com.example.plathome.estate.real.dto.request;

import com.example.plathome.estate.common.Floor;
import com.example.plathome.estate.common.Option;
import com.example.plathome.estate.common.RentalType;
import com.example.plathome.estate.common.RoomType;
import com.example.plathome.estate.real.domain.Estate;
import com.example.plathome.estate.real.domain.constant.Area;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;

@Builder
public record EstateForm(
        @NotNull Long memberId,
        @NotBlank @Size(max = 255) String location,
        @NotNull Area area,
        @NotNull RoomType roomType,
        @NotNull RentalType rentalType,
        @NotNull Floor floor,
        @NotBlank String contractUrl,
        @NotBlank @Size(max = 16777215) String context,
        @NotBlank String thumbNailUrl,
        @NotNull LocalDate contractTerm,
        @NotNull Option option,
        @NotNull @DecimalMin("0.0") @Digits(integer=3, fraction=2) Double squareFeet,
        @NotNull @DecimalMin("-180.0") @DecimalMax("180.0") Double lng,
        @NotNull @DecimalMin("-90.0") @DecimalMax("90.0") Double lat,
        @NotNull @Min(0) Integer deposit,
        @NotNull @Min(0) Integer maintenanceFee,
        @NotNull @Min(0) Integer monthlyRent
) {

    public static EstateFormBuilder of() {
        return EstateForm.builder();
    }

    public Estate toEntity() {
        return Estate.builder()
                .memberId(this.memberId())
                .location(this.location())
                .area(this.area())
                .roomType(this.roomType())
                .rentalType(this.rentalType())
                .floor(this.floor())
                .contractUrl(this.contractUrl())
                .context(this.context())
                .thumbNailUrl(this.thumbNailUrl())
                .contractTerm(this.contractTerm())
                .option(this.option())
                .squareFeet(this.squareFeet())
                .lng(this.lng())
                .lat(this.lat())
                .deposit(this.deposit())
                .maintenanceFee(this.maintenanceFee())
                .monthlyRent(this.monthlyRent()).build();
    }
}
