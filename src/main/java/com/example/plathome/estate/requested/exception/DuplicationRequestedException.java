package com.example.plathome.estate.requested.exception;

import com.example.plathome.global.exception.DuplicationException;

import static com.example.plathome.global.error.ErrorStaticField.DUP_REQUESTED;

public class DuplicationRequestedException extends DuplicationException {
    private static final String MESSAGE = DUP_REQUESTED;
    public DuplicationRequestedException() {
        super(MESSAGE);
    }
}
