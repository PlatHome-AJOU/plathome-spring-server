package com.example.plathome.member.exception;

import com.example.plathome.global.exception.ForbiddenException;

import static com.example.plathome.global.error.ErrorStaticField.ERROR_MEMBER_FORBIDDEN;

public class ForbiddenMemberException extends ForbiddenException {

    private static final String MESSAGE = ERROR_MEMBER_FORBIDDEN;
    public ForbiddenMemberException() {
        super(MESSAGE);
    }
}
