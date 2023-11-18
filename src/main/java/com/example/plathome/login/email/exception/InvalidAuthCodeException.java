package com.example.plathome.login.email.exception;


import com.example.plathome.global.exception.UnauthorizedException;

import static com.example.plathome.global.error.ErrorStaticField.INVALID_ACCESS_TOKEN;
import static com.example.plathome.global.error.ErrorStaticField.INVALID_AUTH_CODE;

public class InvalidAuthCodeException extends UnauthorizedException {
    private static final String MESSAGE = INVALID_AUTH_CODE;
    public InvalidAuthCodeException() {
        super(MESSAGE);
    }
}
