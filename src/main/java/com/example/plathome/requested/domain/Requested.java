package com.example.plathome.requested.domain;

import com.example.plathome.global.domain.AuditingFields;
import com.example.plathome.requested.domain.constant.RentalType;
import com.example.plathome.requested.domain.converter.OptionsConverter;
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
public class Requested {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requested_id")
    private Long id;

    private Long memberId;

    private String location;

    @Enumerated(EnumType.STRING)
    private RentalType rentalType;

    @Column(length = 100)
    private String contractPath;

    private LocalDate contractDate;


    @Convert(converter = OptionsConverter.class)
    private Set<Option> options = new LinkedHashSet<>();

    private double squareFeet;

    private int maintenanceFee;

    private int monthlyRent;

    @Embedded
    private AuditingFields auditingFields;

    @Builder
    public Requested(Long memberId, String location, RentalType rentalType, String contractPath, Set<Option> options, int maintenanceFee, int monthlyRent) {
        this.memberId = memberId;
        this.location = location;
        this.rentalType = rentalType;
        this.contractPath = contractPath;
        this.options = options;
        this.maintenanceFee = maintenanceFee;
        this.monthlyRent = monthlyRent;
    }
}
