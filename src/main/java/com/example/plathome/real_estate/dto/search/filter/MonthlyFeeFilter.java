package com.example.plathome.real_estate.dto.search.filter;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record MonthlyFeeFilter(
        @NotNull Integer min,
        @NotNull Integer max
) {

}
