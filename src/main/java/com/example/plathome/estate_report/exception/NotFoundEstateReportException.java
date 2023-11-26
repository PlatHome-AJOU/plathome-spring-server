package com.example.plathome.estate_report.exception;

import com.example.plathome.global.exception.NotFoundException;

import static com.example.plathome.global.error.ErrorStaticField.ESTATE_REPORT_NOT_FOUND;

public class NotFoundEstateReportException extends NotFoundException {
    private static final String MESSAGE = ESTATE_REPORT_NOT_FOUND;
    public NotFoundEstateReportException() {
        super(MESSAGE);
    }
}
