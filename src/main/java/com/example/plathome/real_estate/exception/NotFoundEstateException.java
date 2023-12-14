package com.example.plathome.real_estate.exception;

import com.example.plathome.global.exception.NotFoundException;

import static com.example.plathome.global.error.ErrorStaticField.ERROR_ESTATE_NOT_FOUND;

public class NotFoundEstateException extends NotFoundException {
    private static final String MESSAGE = ERROR_ESTATE_NOT_FOUND;
    public NotFoundEstateException() {
        super(MESSAGE);
    }
}
