package com.example.plathome.member.exception;

import com.example.plathome.global.exception.ForbiddenException;

import static com.example.plathome.global.error.ErrorStaticField.MEMBER_FORBIDDEN;

public class ForbiddenMemberException extends ForbiddenException {

    private static final String MESSAGE = MEMBER_FORBIDDEN;
    public ForbiddenMemberException() {
        super(MESSAGE);
    }
}
