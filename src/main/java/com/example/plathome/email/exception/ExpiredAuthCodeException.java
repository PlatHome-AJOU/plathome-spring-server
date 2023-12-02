package com.example.plathome.email.exception;

import com.example.plathome.global.exception.UnauthorizedException;

import static com.example.plathome.global.error.ErrorStaticField.ERROR_LOGIN_EXPIRED_AUTH_CODE;

public class ExpiredAuthCodeException extends UnauthorizedException {

    private static final String MESSAGE = ERROR_LOGIN_EXPIRED_AUTH_CODE;
    public ExpiredAuthCodeException() {
        super(ERROR_LOGIN_EXPIRED_AUTH_CODE);
    }
}
