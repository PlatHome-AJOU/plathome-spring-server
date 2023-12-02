package com.example.plathome.member.exception;


import com.example.plathome.global.exception.NotMatchException;

import static com.example.plathome.global.error.ErrorStaticField.ERROR_MEMBER_INVALID_PASSWORD;

public class NotMatchMemberPasswordException extends NotMatchException {

    private static final String MESSAGE = ERROR_MEMBER_INVALID_PASSWORD;
    public NotMatchMemberPasswordException() {
        super(MESSAGE);
    }
}
