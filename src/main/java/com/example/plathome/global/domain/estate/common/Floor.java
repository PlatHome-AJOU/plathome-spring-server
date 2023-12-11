package com.example.plathome.global.domain.estate.common;

import lombok.Getter;

@Getter
public enum Floor {
    FIRST("1층"),
    SECOND("2층"),
    THIRD("3층"),
    FOURTH("4층"),
    FIFTH("5층"),
    SIXTH("6층"),
    SEVENTH_UPPER("7층 이상"),
    TOP("옥탑방"),
    UNDER("반지하");

    private final String description;

    Floor(String description) {
        this.description = description;
    }
}
