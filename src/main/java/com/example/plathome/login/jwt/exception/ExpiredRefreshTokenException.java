package com.example.plathome.login.jwt.exception;


import com.example.plathome.global.exception.UnauthorizedException;

import static com.example.plathome.global.error.ErrorStaticField.EXPIRED_REFRESH_TOKEN;

public class ExpiredRefreshTokenException extends UnauthorizedException {
    private static final String MESSAGE = EXPIRED_REFRESH_TOKEN;
    public ExpiredRefreshTokenException() {
        super(MESSAGE);
    }
}
