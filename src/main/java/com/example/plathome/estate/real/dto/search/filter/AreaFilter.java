package com.example.plathome.estate.real.dto.search.filter;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AreaFilter(
        @NotNull Boolean gwanggyo,
        @NotNull Boolean ingyedong,
        @NotNull Boolean uman,
        @NotNull Boolean woncheon,
        @NotNull Boolean maetan
) {
}

