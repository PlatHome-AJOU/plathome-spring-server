package com.example.plathome.member.exception;


import com.example.plathome.global.exception.DuplicationException;

import static com.example.plathome.global.error.ErrorStaticField.DUP_LOGIN_ID;

public class DuplicationMemberException extends DuplicationException {

    private static final String MESSAGE = DUP_LOGIN_ID;

    public DuplicationMemberException() {
        super(MESSAGE);
    }
}
