package com.example.plathome.requested_estate.dto.response;

import com.example.plathome.global.domain.estate.common.Option;
import com.example.plathome.global.domain.estate.common.Floor;
import com.example.plathome.global.domain.estate.common.RentalType;
import com.example.plathome.global.domain.estate.common.RoomType;
import com.example.plathome.requested_estate.domain.Requested;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;

@Builder
public record RequestedResponse(
        long id,
        long memberId,
        String location,
        String context,
        RoomType roomType,
        RentalType rentalType,
        Floor floor,
        String contractUrl,
        Set<String> thumbNailUrls,
        LocalDate contractTerm,
        Option option,
        double squareFeet,
        int deposit,
        int maintenanceFee,
        int monthlyRent
) {

    public static RequestedResponseBuilder of() {
        return RequestedResponse.builder();
    }

    public static RequestedResponse from(Requested entity, Set<String> thumbNailUrls) {
        return RequestedResponse.of()
                .id(entity.getId())
                .memberId(entity.getMemberId())
                .location(entity.getLocation())
                .context(entity.getContext())
                .roomType(entity.getRoomType())
                .rentalType(entity.getRentalType())
                .floor(entity.getFloor())
                .contractUrl(entity.getContractUrl())
                .thumbNailUrls(thumbNailUrls)
                .contractTerm(entity.getContractTerm())
                .option(entity.getOption())
                .squareFeet(entity.getSquareFeet())
                .deposit(entity.getDeposit())
                .maintenanceFee(entity.getMaintenanceFee())
                .monthlyRent(entity.getMonthlyRent()).build();
    }
}
