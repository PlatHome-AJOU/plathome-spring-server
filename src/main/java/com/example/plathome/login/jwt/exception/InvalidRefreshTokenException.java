package com.example.plathome.login.jwt.exception;


import com.example.plathome.global.exception.UnauthorizedException;

import static com.example.plathome.global.error.ErrorStaticField.INVALID_REFRESH_TOKEN;

public class InvalidRefreshTokenException extends UnauthorizedException {
    private static final String MESSAGE = INVALID_REFRESH_TOKEN;
    public InvalidRefreshTokenException() {
        super(MESSAGE);
    }
}
