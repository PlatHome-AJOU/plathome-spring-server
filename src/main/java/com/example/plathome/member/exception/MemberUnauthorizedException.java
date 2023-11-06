package com.example.plathome.member.exception;


import com.example.plathome.global.exception.UnauthorizedException;

import static com.example.plathome.global.error.ErrorStaticField.MEMBER_UNAUTHORIZED;

public class MemberUnauthorizedException extends UnauthorizedException {

    private static final String MESSAGE = MEMBER_UNAUTHORIZED;
    public MemberUnauthorizedException() {
        super(MESSAGE);
    }
}
