package com.example.plathome.user_report.exception;

import com.example.plathome.global.exception.NotFoundException;

import static com.example.plathome.global.error.ErrorStaticField.ERROR_USER_REPORT_NOT_FOUND;

public class NotFoundUserReportException extends NotFoundException {
    private static final String MESSAGE = ERROR_USER_REPORT_NOT_FOUND;
    public NotFoundUserReportException() {
        super(MESSAGE);
    }
}
