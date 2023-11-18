package com.example.plathome.login.jwt.exception;


import com.example.plathome.global.exception.UnauthorizedException;

import static com.example.plathome.global.error.ErrorStaticField.INVALID_ACCESS_TOKEN;

public class InvalidAccessTokenException extends UnauthorizedException {
    private static final String MESSAGE = INVALID_ACCESS_TOKEN;
    public InvalidAccessTokenException() {
        super(MESSAGE);
    }
}
