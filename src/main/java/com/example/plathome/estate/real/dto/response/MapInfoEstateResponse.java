package com.example.plathome.estate.real.dto.response;

import com.example.plathome.estate.real.domain.Estate;
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
