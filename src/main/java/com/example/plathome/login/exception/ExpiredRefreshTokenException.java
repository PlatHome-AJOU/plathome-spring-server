package com.example.plathome.login.exception;


import com.example.plathome.global.exception.UnauthorizedException;

import static com.example.plathome.global.error.ErrorStaticField.ERROR_LOGIN_EXPIRED_REFRESH_TOKEN;

public class ExpiredRefreshTokenException extends UnauthorizedException {
    private static final String MESSAGE = ERROR_LOGIN_EXPIRED_REFRESH_TOKEN;
    public ExpiredRefreshTokenException() {
        super(MESSAGE);
    }
}
