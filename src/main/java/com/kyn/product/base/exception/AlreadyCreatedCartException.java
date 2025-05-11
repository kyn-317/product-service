package com.kyn.product.base.exception;

public class AlreadyCreatedCartException extends RuntimeException {
    public AlreadyCreatedCartException(String message) {
        super(message);
    }
}
