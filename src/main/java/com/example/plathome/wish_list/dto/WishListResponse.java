package com.example.plathome.wish_list.dto;

import com.example.plathome.estate.common.Floor;
import com.example.plathome.estate.common.Option;
import com.example.plathome.estate.common.RentalType;
import com.example.plathome.estate.common.RoomType;
import com.example.plathome.estate.real.domain.constant.Area;
import com.example.plathome.estate.real.dto.response.EstateResponse;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record WishListResponse(
        long memberId,
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

    public static WishListResponseBuilder of() {
        return WishListResponse.builder();
    }


    public static WishListResponse customfrom(EstateResponse estateResponse){
        return WishListResponse.of()
                .location(estateResponse.location())
                .area(estateResponse.area())
                .monthlyRent(estateResponse.monthlyRent())
                .maintenanceFee(estateResponse.maintenanceFee())
                .squareFeet(estateResponse.squareFeet())
                .deposit(estateResponse.deposit())
                .roomType(estateResponse.roomType())
                .build();
    }

    public static WishListResponse from(EstateResponse estateResponse) {
        return WishListResponse.of()
                .memberId(estateResponse.memberId())
                .location(estateResponse.location())
                .area(estateResponse.area())
                .roomType(estateResponse.roomType())
                .rentalType(estateResponse.rentalType())
                .floor(estateResponse.floor())
                .contractTerm(estateResponse.contractTerm())
                .option(estateResponse.option())
                .squareFeet(estateResponse.squareFeet())
                .deposit(estateResponse.deposit())
                .maintenanceFee(estateResponse.maintenanceFee())
                .monthlyRent(estateResponse.monthlyRent()).build();
    }
}