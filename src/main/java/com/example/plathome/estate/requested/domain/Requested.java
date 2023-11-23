package com.example.plathome.estate.requested.domain;

import com.example.plathome.estate.Option;
import com.example.plathome.estate.constant.RentalType;
import com.example.plathome.global.domain.AuditingFields;
import com.example.plathome.estate.converter.OptionsConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Requested extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requested_id")
    private Long id;

    private String userId;

    private String location;

    @Enumerated(EnumType.STRING)
    private RentalType rentalType;

    @Column(length = 100)
    private String contractUrl;

    private LocalDate contractTerm;

    @Convert(converter = OptionsConverter.class)
    private Set<Option> options = new LinkedHashSet<>();

    private double squareFeet;

    private int maintenanceFee;

    private int monthlyRent;

    @Builder
    public Requested(String userId, String location, RentalType rentalType, String contractUrl, LocalDate contractTerm, Set<Option> options, double squareFeet, int maintenanceFee, int monthlyRent) {
        this.userId = userId;
        this.location = location;
        this.rentalType = rentalType;
        this.contractUrl = contractUrl;
        this.contractTerm = contractTerm;
        this.options = options;
        this.squareFeet = squareFeet;
        this.maintenanceFee = maintenanceFee;
        this.monthlyRent = monthlyRent;
    }

    public void updateForm(String location, RentalType rentalType, LocalDate contractTerm, Set<Option> options, double squareFeet, int maintenanceFee, int monthlyRent) {
        this.location = location;
        this.rentalType = rentalType;
        this.contractTerm = contractTerm;
        this.options = options;
        this.squareFeet = squareFeet;
        this.maintenanceFee = maintenanceFee;
        this.monthlyRent = monthlyRent;
    }

    public void updateUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }
}
