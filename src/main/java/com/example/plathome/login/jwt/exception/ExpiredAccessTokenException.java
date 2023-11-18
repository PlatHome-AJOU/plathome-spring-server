package com.example.plathome.login.jwt.exception;


import com.example.plathome.global.exception.UnauthorizedException;

import static com.example.plathome.global.error.ErrorStaticField.EXPIRED_ACCESS_TOKEN;

public class ExpiredAccessTokenException extends UnauthorizedException {
    public static final String MESSAGE = EXPIRED_ACCESS_TOKEN;
    public ExpiredAccessTokenException() {
        super(MESSAGE);
    }
}


