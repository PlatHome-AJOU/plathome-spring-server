package com.example.plathome.login.jwt.dto.request.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

import static com.example.plathome.global.error.ErrorStaticField.EMAIL_FORM_ERROR;

@Documented
@Constraint(validatedBy = {})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@Pattern(regexp=".+@ajou\\.ac\\.kr$")
public @interface AjouEmail {
    String message() default EMAIL_FORM_ERROR;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}