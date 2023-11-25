package com.example.plathome.estate.requested.dto.response;

import com.example.plathome.estate.common.Option;
import com.example.plathome.estate.common.Floor;
import com.example.plathome.estate.common.RentalType;
import com.example.plathome.estate.common.RoomType;
import com.example.plathome.estate.requested.domain.Requested;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record RequestedResponse(
        Long id,
        String userId,
        String location,
        RoomType roomType,
        RentalType rentalType,
        Floor floor,
        String contractUrl,
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

    public static RequestedResponse from(Requested entity) {
        return RequestedResponse.of()
                .id(entity.getId())
                .userId(entity.getUserId())
                .location(entity.getLocation())
                .roomType(entity.getRoomType())
                .rentalType(entity.getRentalType())
                .floor(entity.getFloor())
                .contractUrl(entity.getContractUrl())
                .contractTerm(entity.getContractTerm())
                .option(entity.getOption())
                .squareFeet(entity.getSquareFeet())
                .deposit(entity.getDeposit())
                .maintenanceFee(entity.getMaintenanceFee())
                .monthlyRent(entity.getMonthlyRent()).build();
    }
}
