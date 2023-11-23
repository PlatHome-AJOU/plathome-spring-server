package com.example.plathome.estate.requested.dto.response;

import com.example.plathome.estate.Option;
import com.example.plathome.estate.requested.domain.Requested;
import com.example.plathome.estate.constant.RentalType;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;

@Builder
public record RequestedResponse(
        Long id,
        String userId,
        String location,
        RentalType rentalType,
        String contractUrl,
        LocalDate contractTerm,
        Set<Option> options,
        double squareFeet,
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
                .rentalType(entity.getRentalType())
                .contractUrl(entity.getContractUrl())
                .contractTerm(entity.getContractTerm())
                .options(entity.getOptions())
                .squareFeet(entity.getSquareFeet())
                .maintenanceFee(entity.getMaintenanceFee())
                .monthlyRent(entity.getMonthlyRent()).build();
    }
}
