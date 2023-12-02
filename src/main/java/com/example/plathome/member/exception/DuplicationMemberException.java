package com.example.plathome.member.exception;


import com.example.plathome.global.exception.DuplicationException;

public class DuplicationMemberException extends DuplicationException {

    public DuplicationMemberException(String message) {
        super(message);
    }
}
