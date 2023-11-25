package com.example.plathome.estate.real.dto.response;

import com.example.plathome.estate.common.Floor;
import com.example.plathome.estate.common.Option;
import com.example.plathome.estate.common.RentalType;
import com.example.plathome.estate.common.RoomType;
import com.example.plathome.estate.real.domain.Estate;
import com.example.plathome.estate.real.domain.constant.Area;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EstateResponse(
        String userId,
        String location,
        Area area,
        RoomType roomType,
        RentalType rentalType,
        Floor floor,
        LocalDate contractTerm,
        Option option,
        double squareFeet,
        int deposit,
        int maintenanceFee,
        int monthlyRent
) {

    public static EstateResponseBuilder of() {
        return EstateResponse.builder();
    }

    public static EstateResponse from(Estate entity) {
        return EstateResponse.of()
                .userId(entity.getUserId())
                .location(entity.getLocation())
                .area(entity.getArea())
                .roomType(entity.getRoomType())
                .rentalType(entity.getRentalType())
                .floor(entity.getFloor())
                .contractTerm(entity.getContractTerm())
                .option(entity.getOption())
                .squareFeet(entity.getSquareFeet())
                .deposit(entity.getDeposit())
                .maintenanceFee(entity.getMaintenanceFee())
                .monthlyRent(entity.getMonthlyRent()).build();
    }
}
