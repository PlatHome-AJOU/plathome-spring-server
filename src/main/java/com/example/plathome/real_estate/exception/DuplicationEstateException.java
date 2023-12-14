package com.example.plathome.real_estate.exception;

import com.example.plathome.global.exception.DuplicationException;

import static com.example.plathome.global.error.ErrorStaticField.ERROR_ESTATE_DUPLICATE;

public class DuplicationEstateException extends DuplicationException {
    private static final String MESSAGE = ERROR_ESTATE_DUPLICATE;
    public DuplicationEstateException() {
        super(MESSAGE);
    }
}
