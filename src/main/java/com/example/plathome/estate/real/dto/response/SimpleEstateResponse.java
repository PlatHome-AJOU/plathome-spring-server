package com.example.plathome.estate.real.dto.response;

import com.example.plathome.estate.common.RentalType;
import com.example.plathome.estate.real.domain.Estate;
import lombok.Builder;

@Builder
public record SimpleEstateResponse(
        long memberId,
        String location,
        RentalType rentalType
) {

    public static SimpleEstateResponseBuilder of() {
        return SimpleEstateResponse.builder();
    }

    public static SimpleEstateResponse from(Estate entity) {
        return SimpleEstateResponse.of()
                .memberId(entity.getMemberId())
                .location(entity.getLocation())
                .rentalType(entity.getRentalType()).build();
    }
}
