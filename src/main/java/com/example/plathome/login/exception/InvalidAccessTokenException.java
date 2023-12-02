package com.example.plathome.login.exception;


import com.example.plathome.global.exception.UnauthorizedException;

import static com.example.plathome.global.error.ErrorStaticField.ERROR_LOGIN_INVALID_ACCESS_TOKEN;

public class InvalidAccessTokenException extends UnauthorizedException {
    private static final String MESSAGE = ERROR_LOGIN_INVALID_ACCESS_TOKEN;
    public InvalidAccessTokenException() {
        super(MESSAGE);
    }
}
