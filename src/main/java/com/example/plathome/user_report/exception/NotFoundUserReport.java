package com.example.plathome.user_report.exception;

import com.example.plathome.global.exception.NotFoundException;

import static com.example.plathome.global.error.ErrorStaticField.USER_REPORT_NOT_FOUND;

public class NotFoundUserReport extends NotFoundException {
    private static final String MESSAGE = USER_REPORT_NOT_FOUND;
    public NotFoundUserReport() {
        super(MESSAGE);
    }
}
