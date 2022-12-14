package com.apu.order.exceptions;

public class CustomerNotFoundException extends Exception {

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public CustomerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
