package com.example.plathome.estate.requested.exception;

import com.example.plathome.global.exception.NotFoundException;

import static com.example.plathome.global.error.ErrorStaticField.REQUESTED_NOT_FOUND;

public class NotFoundRequestedException extends NotFoundException {
    private static final String MESSAGE = REQUESTED_NOT_FOUND;
    public NotFoundRequestedException() {
        super(MESSAGE);
    }
}
