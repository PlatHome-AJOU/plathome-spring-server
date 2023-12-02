package com.example.plathome.estate.requested.exception;

import com.example.plathome.global.exception.DuplicationException;

import static com.example.plathome.global.error.ErrorStaticField.ERROR_ESTATE_REQUEST_DUPLICATE;

public class DuplicationRequestedException extends DuplicationException {
    private static final String MESSAGE = ERROR_ESTATE_REQUEST_DUPLICATE;
    public DuplicationRequestedException() {
        super(MESSAGE);
    }
}
