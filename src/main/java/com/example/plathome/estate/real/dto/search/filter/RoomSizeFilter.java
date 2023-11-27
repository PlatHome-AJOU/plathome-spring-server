package com.example.plathome.estate.real.dto.search.filter;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RoomSizeFilter(
        @NotNull Double min,
        @NotNull Double max
) {

}

