package com.example.plathome.real_estate.dto.search.filter;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RoomTypeFilter(
        @NotNull Boolean studio,
        @NotNull Boolean two_threeRoom,
        @NotNull Boolean officetel,
        @NotNull Boolean apartment
) {

}
