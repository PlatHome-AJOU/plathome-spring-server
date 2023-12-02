package com.example.plathome.global.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

import static com.example.plathome.global.error.ErrorStaticField.BAD_REQUEST;
import static com.example.plathome.global.error.ErrorStaticField.ERROR_REQUEST_BINDING;


@Getter
public class BindingException extends RuntimeException{
    private static final int STATUS_CODE = BAD_REQUEST;

    private static final String MESSAGE = ERROR_REQUEST_BINDING;
    private BindingResult bindingResult;


    public BindingException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public static void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BindingException(bindingResult);
        }
    }
}
