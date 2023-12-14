package com.example.plathome.real_estate.dto.search.filter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record OptionFilter(
        @NotNull Boolean elevator,
        @NotNull Boolean park,
        @NotNull Boolean cctv,
        @NotNull Boolean doorLock,
        @NotNull Boolean pet,
        @NotNull Boolean veranda,
        @NotBlank String bunner,
        @NotBlank String airConditioner,
        @NotNull Boolean refrigerator,
        @NotNull Boolean sink,
        @NotNull Boolean tv,
        @NotNull Boolean internet,
        @NotNull Boolean bed,
        @NotNull Boolean desk,
        @NotNull Boolean microwave,
        @NotNull Boolean closet,
        @NotNull Boolean shoeRack,
        @NotNull Boolean bidet,
        @NotNull Boolean interPhone,
        @NotNull Boolean parking,
        @NotNull Boolean security,
        @NotNull Boolean deliveryBox,
        @NotNull Boolean buildingEntrance,
        @NotNull Boolean washingMachine
) {

}

