package com.apu.user.exceptions;

public class PayRollNotFoundException extends Exception {

    public PayRollNotFoundException(String message) {
        super(message);
    }

    public PayRollNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public PayRollNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
