package com.example.plathome.wish_list.exception;

import com.example.plathome.global.exception.UnauthorizedException;

public class UnAuthorizedWishListException extends UnauthorizedException {
    public UnAuthorizedWishListException (String message) {
        super(message);
    }
}
