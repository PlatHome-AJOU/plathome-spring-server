package com.example.plathome.estate.common;

import lombok.Getter;

@Getter
public enum Floor {
    FIRST("1층"),
    SECOND("2층"),
    THIRD("3층"),
    FORTH("4층"),
    FIFTH("5층"),
    SIXTH("6층"),
    SEVEN_UPPER("7층 이상"),
    TOP("옥탑방"),
    UPDER("반지하");

    private final String description;

    Floor(String description) {
        this.description = description;
    }
}
