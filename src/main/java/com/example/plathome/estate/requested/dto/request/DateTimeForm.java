package com.example.plathome.estate.requested.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DateTimeForm (
        @NotNull LocalDateTime fileName
){
}
