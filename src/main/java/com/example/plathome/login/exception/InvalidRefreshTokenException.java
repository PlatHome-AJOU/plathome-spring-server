package com.example.plathome.login.exception;


import com.example.plathome.global.exception.UnauthorizedException;

import static com.example.plathome.global.error.ErrorStaticField.ERROR_LOGIN_INVALID_REFRESH_TOKEN;

public class InvalidRefreshTokenException extends UnauthorizedException {
    private static final String MESSAGE = ERROR_LOGIN_INVALID_REFRESH_TOKEN;
    public InvalidRefreshTokenException() {
        super(MESSAGE);
    }
}
