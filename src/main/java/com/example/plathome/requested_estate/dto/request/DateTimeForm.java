package com.example.plathome.requested_estate.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DateTimeForm (
        @NotNull LocalDateTime fileName
){
}
