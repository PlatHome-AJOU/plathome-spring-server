package com.example.plathome.global.domain.estate.common;

import jakarta.persistence.Embeddable;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Option {
    private Boolean elevator;
    private Boolean park;
    private Boolean cctv;
    private Boolean doorLock;
    private Boolean pet;
    private Boolean veranda;
    private String bunner; // "induction" | "gas" | "false"
    private String airConditioner; // "top" | "wall" | "stand" | "false"
    private Boolean refrigerator;
    private Boolean sink;
    private Boolean tv;
    private Boolean internet;
    private Boolean bed;
    private Boolean desk;
    private Boolean microwave;
    private Boolean closet;
    private Boolean shoeRack;
    private Boolean bidet;
    private Boolean interPhone;
    private Boolean parking;
    private Boolean security;
    private Boolean deliveryBox;
    private Boolean buildingEntrance;
    private Boolean washingMachine;

    @Builder
    public Option(Boolean elevator, Boolean park, Boolean cctv, Boolean doorLock, Boolean pet, Boolean veranda, String bunner, String airConditioner, Boolean refrigerator, Boolean sink, Boolean tv, Boolean internet, Boolean bed, Boolean desk, Boolean microwave, Boolean closet, Boolean shoeRack, Boolean bidet, Boolean interPhone, Boolean parking, Boolean security, Boolean deliveryBox, Boolean buildingEntrance, Boolean washingMachine) {
        this.elevator = elevator;
        this.park = park;
        this.cctv = cctv;
        this.doorLock = doorLock;
        this.pet = pet;
        this.veranda = veranda;
        this.bunner = bunner;
        this.airConditioner = airConditioner;
        this.refrigerator = refrigerator;
        this.sink = sink;
        this.tv = tv;
        this.internet = internet;
        this.bed = bed;
        this.desk = desk;
        this.microwave = microwave;
        this.closet = closet;
        this.shoeRack = shoeRack;
        this.bidet = bidet;
        this.interPhone = interPhone;
        this.parking = parking;
        this.security = security;
        this.deliveryBox = deliveryBox;
        this.buildingEntrance = buildingEntrance;
        this.washingMachine = washingMachine;
    }
}
