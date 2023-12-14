package com.example.plathome.real_estate.domain.types;

public enum Area {
    UMAN("우만동"),
    INGYEDONG("인계동"),
    MAETAN("매탄동"),
    WONCHEON("원천동"),
    GWANGGYO("광교동");

    private final String description;

    Area(String description) {
        this.description = description;
    }
}
