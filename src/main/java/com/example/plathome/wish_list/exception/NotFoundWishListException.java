package com.example.plathome.wish_list.exception;

import com.example.plathome.global.exception.NotFoundException;

import static com.example.plathome.global.error.ErrorStaticField.ERROR_WISH_LIST_NOT_FOUND;

public class NotFoundWishListException extends NotFoundException {
    private static final String MESSAGE = ERROR_WISH_LIST_NOT_FOUND;
    public NotFoundWishListException() {
        super(MESSAGE);
    }
}
