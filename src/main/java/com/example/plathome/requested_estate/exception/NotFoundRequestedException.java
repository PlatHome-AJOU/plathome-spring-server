package com.example.plathome.requested_estate.exception;

import com.example.plathome.global.exception.NotFoundException;

import static com.example.plathome.global.error.ErrorStaticField.ERROR_ESTATE_REQUEST_NOT_FOUND;

public class NotFoundRequestedException extends NotFoundException {
    private static final String MESSAGE = ERROR_ESTATE_REQUEST_NOT_FOUND;
    public NotFoundRequestedException() {
        super(MESSAGE);
    }
}
