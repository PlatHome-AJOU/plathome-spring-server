package com.example.plathome.real_estate.dto.response;

import com.example.plathome.real_estate.domain.Estate;
import lombok.Builder;

@Builder
public record MapInfoEstateResponse(
        long memberId,
        double lng,
        double lat
) {

    public static MapInfoEstateResponseBuilder of() {
        return MapInfoEstateResponse.builder();
    }

    public static MapInfoEstateResponse from(Estate entity) {
        return MapInfoEstateResponse.of()
                .memberId(entity.getMemberId())
                .lng(entity.getLng())
                .lat(entity.getLat()).build();
    }
}
