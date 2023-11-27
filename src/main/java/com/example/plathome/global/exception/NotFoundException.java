package com.example.plathome.global.exception;

import lombok.Getter;

import static com.example.plathome.global.error.ErrorStaticField.NOT_FOUND;


@Getter
public class NotFoundException extends RuntimeException{
    private static final int STATUS_CODE = NOT_FOUND;
    private String message;
    public NotFoundException(String message) {
        this.message = message;
    }
}
