package com.example.plathome.real_estate.dto.search.filter;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RentTypeFilter(
        @NotNull Boolean monthly,
        @NotNull Boolean jeonse
) {

}
