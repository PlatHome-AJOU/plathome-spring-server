package com.example.plathome.email.exception;


import com.example.plathome.global.exception.UnauthorizedException;

import static com.example.plathome.global.error.ErrorStaticField.ERROR_LOGIN_INVALID_AUTH_CODE;

public class InvalidAuthCodeException extends UnauthorizedException {
    private static final String MESSAGE = ERROR_LOGIN_INVALID_AUTH_CODE;
    public InvalidAuthCodeException() {
        super(MESSAGE);
    }
}
