package com.example.plathome.requested_estate.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ThumbNail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thumb_nail_id")
    private Long id;

    private Long memberId;
    private String url;

    @Builder
    public ThumbNail(Long memberId, String url) {
        this.memberId = memberId;
        this.url = url;
    }
}
