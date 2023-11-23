package com.example.plathome.schedule.domain;

import com.example.plathome.global.domain.AuditingFields;
import com.example.plathome.schedule.domain.converter.AvailableDatesConverter;
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
public class Schedule extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @Convert(converter = AvailableDatesConverter.class)
    private Set<AvailableDate> availableDates = new LinkedHashSet<>();

    private LocalDate selectedDate;

    @Column(name = "member1_id")
    private Long member1Id;

    @Column(name = "member2_id")
    private Long member2Id;

    @Builder
    public Schedule(Set<AvailableDate> availableDateSet, LocalDate selectedDate, Long member1Id, Long member2Id) {
        this.availableDates = availableDateSet;
        this.selectedDate = selectedDate;
        this.member1Id = member1Id;
        this.member2Id = member2Id;
    }
}
