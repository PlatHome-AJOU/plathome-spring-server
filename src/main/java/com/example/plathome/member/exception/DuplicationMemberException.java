package com.example.plathome.member.exception;


import com.example.plathome.global.exception.DuplicationException;

import static com.example.plathome.global.error.ErrorStaticField.DUP_EMAIL;

public class DuplicationMemberException extends DuplicationException {

    public DuplicationMemberException(String message) {
        super(message);
    }
}
