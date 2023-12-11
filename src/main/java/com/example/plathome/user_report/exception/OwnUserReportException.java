package com.example.plathome.user_report.exception;

import com.example.plathome.global.exception.ForbiddenException;

import static com.example.plathome.global.error.ErrorStaticField.ERROR_USER_REPORT_OWN_REPORT;

public class OwnUserReportException extends ForbiddenException {
    private static final String MESSAGE = ERROR_USER_REPORT_OWN_REPORT;
    public OwnUserReportException() {
        super(MESSAGE);
    }
}
