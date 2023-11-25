package com.example.plathome.estate.common;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Embeddable
public class Option {
    private Boolean elevator;
    private Boolean park;
    private Boolean cctv;
    private Boolean doorLock;
    private Boolean pet;
    private Boolean veranda;
    private String bunner; // "induction" | "gas" | "none"
    private String airConditioner; // "top" | "wall" | "stand" | "none"
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
    private Boolean interphone;
    private Boolean parking;
    private Boolean security;
    private Boolean deilveryBox;
    private Boolean buildingEntrance;
    private Boolean washingMachine;
}
