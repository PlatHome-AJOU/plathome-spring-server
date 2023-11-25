package com.example.plathome.estate.real.dto.response;

import com.example.plathome.estate.common.RentalType;
import com.example.plathome.estate.real.domain.Estate;
import lombok.Builder;

@Builder
public record SimpleEstateResponse(
        String userId,
        String location,
        RentalType rentalType
) {

    public static SimpleEstateResponseBuilder of() {
        return SimpleEstateResponse.builder();
    }

    public static SimpleEstateResponse from(Estate entity) {
        return SimpleEstateResponse.of()
                .userId(entity.getUserId())
                .location(entity.getLocation())
                .rentalType(entity.getRentalType()).build();
    }
}
