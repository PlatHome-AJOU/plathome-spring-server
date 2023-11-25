package com.example.plathome.estate.common;

import lombok.Getter;

@Getter
public enum RoomType {
    STUDIO("스튜디오"),
    TOW_THREEROOM("투-쓰리 룸"),
    OFFICETEL("오피스텔"),
    APARTMENT("아파트");

    private final String description;

    RoomType(String description) {
        this.description = description;
    }
}
