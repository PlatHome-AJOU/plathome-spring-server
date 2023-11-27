package com.example.plathome.estate.real.dto.search.filter;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record FloorFilter(
        @NotNull Boolean first,
        @NotNull Boolean second,
        @NotNull Boolean third,
        @NotNull Boolean fourth,
        @NotNull Boolean fifth,
        @NotNull Boolean sixth,
        @NotNull Boolean seventhUpper,
        @NotNull Boolean top,
        @NotNull Boolean under
) {

}
