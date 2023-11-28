package com.example.plathome.schedule.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "schedule", uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "room_id", "available_time"}))
public class Schedule{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @Column(name= "available_time")
    private LocalDateTime availableDate;

    @Column(name = "member_id")
    private Long memberId;


    @Column(name = "room_id")
    private String roomId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public Schedule(LocalDateTime availableDate,Long memberId, String roomId, LocalDateTime createdAt) {
        this.availableDate = availableDate;
        this.memberId = memberId;
        this.roomId = roomId;
        this.createdAt = createdAt;
    }
}
