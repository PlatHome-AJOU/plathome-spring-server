package com.example.plathome.login.dto.request.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

import static com.example.plathome.global.error.ErrorStaticField.ERROR_REQUEST_INVALID_EMAIL_FORM;

@Documented
@Constraint(validatedBy = {})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@Pattern(regexp=".+@ajou\\.ac\\.kr$")
public @interface AjouEmail {
    String message() default ERROR_REQUEST_INVALID_EMAIL_FORM;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}