package com.example.plathome.real_estate.dto.response;

import com.example.plathome.global.domain.estate.common.RentalType;
import com.example.plathome.real_estate.domain.Estate;
import lombok.Builder;

@Builder
public record SimpleEstateResponse(
        long memberId,
        String location,
        RentalType rentalType,
        String thumbNailUrl
) {

    public static SimpleEstateResponseBuilder of() {
        return SimpleEstateResponse.builder();
    }

    public static SimpleEstateResponse from(Estate entity) {
        return SimpleEstateResponse.of()
                .memberId(entity.getMemberId())
                .location(entity.getLocation())
                .rentalType(entity.getRentalType())
                .thumbNailUrl(entity.getThumbNailUrl()).build();
    }
}
