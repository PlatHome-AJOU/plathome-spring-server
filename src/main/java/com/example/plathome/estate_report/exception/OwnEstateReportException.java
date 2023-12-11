package com.example.plathome.estate_report.exception;

import com.example.plathome.global.exception.ForbiddenException;

import static com.example.plathome.global.error.ErrorStaticField.ERROR_ESTATE_REPORT_OWN_REPORT;

public class OwnEstateReportException extends ForbiddenException {
    private static final String MESSAGE = ERROR_ESTATE_REPORT_OWN_REPORT;
    public OwnEstateReportException() {
        super(MESSAGE);
    }
}
