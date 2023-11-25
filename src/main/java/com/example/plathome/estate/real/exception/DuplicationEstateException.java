package com.example.plathome.estate.real.exception;

import com.example.plathome.global.exception.DuplicationException;

import static com.example.plathome.global.error.ErrorStaticField.DUP_ESTATE;

public class DuplicationEstateException extends DuplicationException {
    private static final String MESSAGE = DUP_ESTATE;
    public DuplicationEstateException() {
        super(MESSAGE);
    }
}
