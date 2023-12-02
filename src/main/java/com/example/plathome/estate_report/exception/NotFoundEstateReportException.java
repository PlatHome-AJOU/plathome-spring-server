package com.example.plathome.estate_report.exception;

import com.example.plathome.global.exception.NotFoundException;

import static com.example.plathome.global.error.ErrorStaticField.ERROR_REPORT_ESTATE_NOT_FOUND;

public class NotFoundEstateReportException extends NotFoundException {
    private static final String MESSAGE = ERROR_REPORT_ESTATE_NOT_FOUND;
    public NotFoundEstateReportException() {
        super(MESSAGE);
    }
}
