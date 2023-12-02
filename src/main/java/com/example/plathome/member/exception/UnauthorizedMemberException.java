package com.example.plathome.member.exception;


import com.example.plathome.global.exception.UnauthorizedException;

import static com.example.plathome.global.error.ErrorStaticField.ERROR_MEMBER_UNAUTHORIZED;

public class UnauthorizedMemberException extends UnauthorizedException {

    private static final String MESSAGE = ERROR_MEMBER_UNAUTHORIZED;
    public UnauthorizedMemberException() {
        super(MESSAGE);
    }
}
