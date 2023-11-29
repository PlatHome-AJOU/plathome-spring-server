package com.example.plathome.estate.requested.domain;

import com.example.plathome.estate.common.Option;
import com.example.plathome.estate.common.Floor;
import com.example.plathome.estate.common.RentalType;
import com.example.plathome.estate.common.RoomType;
import com.example.plathome.global.domain.AuditingFields;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Requested extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requested_id")
    private Long id;

    private Long memberId;

    @Column(length = 100)
    private String contractUrl;
    private String location;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String context;


    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    private RentalType rentalType;

    @Enumerated(EnumType.STRING)
    private Floor floor;



    private LocalDate contractTerm;

    @Embedded
    private Option option;

    private double squareFeet;
    private int deposit;
    private int maintenanceFee;
    private int monthlyRent;

    @Builder
    public Requested(Long memberId, String location, String context, RoomType roomType, RentalType rentalType, Floor floor, String contractUrl, LocalDate contractTerm, Option option, double squareFeet, int deposit, int maintenanceFee, int monthlyRent) {
        this.memberId = memberId;
        this.location = location;
        this.context = context;
        this.roomType = roomType;
        this.rentalType = rentalType;
        this.floor = floor;
        this.contractUrl = contractUrl;
        this.contractTerm = contractTerm;
        this.option = option;
        this.squareFeet = squareFeet;
        this.deposit = deposit;
        this.maintenanceFee = maintenanceFee;
        this.monthlyRent = monthlyRent;
    }

    public void updateForm(String location, String context, RoomType roomType, RentalType rentalType, Floor floor, LocalDate contractTerm, Option option, double squareFeet, int deposit, int maintenanceFee, int monthlyRent) {
        this.location = location;
        this.context = context;
        this.roomType = roomType;
        this.rentalType = rentalType;
        this.floor = floor;
        this.contractTerm = contractTerm;
        this.option = option;
        this.squareFeet = squareFeet;
        this.deposit = deposit;
        this.maintenanceFee = maintenanceFee;
        this.monthlyRent = monthlyRent;
    }

    public void updateUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }
}
