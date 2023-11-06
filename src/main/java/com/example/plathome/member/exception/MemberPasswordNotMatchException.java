package com.example.plathome.member.exception;


import com.example.plathome.global.exception.NotMatchException;

import static com.example.plathome.global.error.ErrorStaticField.INVALID_PASSWORD;

public class MemberPasswordNotMatchException extends NotMatchException {

    private static final String MESSAGE = INVALID_PASSWORD;
    public MemberPasswordNotMatchException() {
        super(MESSAGE);
    }
}
