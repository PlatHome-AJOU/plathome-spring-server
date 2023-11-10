package com.example.plathome.wish_list.domain;

import com.example.plathome.global.domain.AuditingFields;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_list_id")
    private Long id;

    private Long memberId;

    private Long estateId;

    @Embedded
    private AuditingFields auditingFields;

    @Builder
    public WishList(Long memberId, Long estateId) {
        this.memberId = memberId;
        this.estateId = estateId;
    }
}
