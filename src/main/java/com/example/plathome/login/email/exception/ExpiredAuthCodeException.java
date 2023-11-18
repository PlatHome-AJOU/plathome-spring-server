package com.example.plathome.login.email.exception;

import com.example.plathome.global.exception.UnauthorizedException;

import static com.example.plathome.global.error.ErrorStaticField.EXPIRED_AUTH_CODE;

public class ExpiredAuthCodeException extends UnauthorizedException {

    private static final String MESSAGE = EXPIRED_AUTH_CODE;
    public ExpiredAuthCodeException() {
        super(EXPIRED_AUTH_CODE);
    }
}
