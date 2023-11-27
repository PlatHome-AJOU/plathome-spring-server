package com.example.plathome.wish_list.exception;

import com.example.plathome.global.exception.DuplicationException;

public class DuplicateWishListException  extends DuplicationException{
    public DuplicateWishListException (String message) {
        super(message);
    }
}
