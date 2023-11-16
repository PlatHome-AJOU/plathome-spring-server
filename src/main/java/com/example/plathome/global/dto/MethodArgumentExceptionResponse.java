package com.example.plathome.global.dto;

import org.springframework.validation.FieldError;

import java.util.Map;

public record MethodArgumentExceptionResponse(
        int statusCode,
        Map<String, String> validation
) {

    public void addValidation(final FieldError fieldError) {
        validation.put(fieldError.getField(), fieldError.getDefaultMessage());
    }
}