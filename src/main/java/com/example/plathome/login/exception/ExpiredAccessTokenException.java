package com.example.plathome.login.exception;


import com.example.plathome.global.exception.UnauthorizedException;

import static com.example.plathome.global.error.ErrorStaticField.ERROR_LOGIN_EXPIRED_ACCESS_TOKEN;

public class ExpiredAccessTokenException extends UnauthorizedException {
    public static final String MESSAGE = ERROR_LOGIN_EXPIRED_ACCESS_TOKEN;
    public ExpiredAccessTokenException() {
        super(MESSAGE);
    }
}


