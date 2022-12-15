package com.apu.payment.exceptions;

public class PaymentNotFoundException extends Exception {

    public PaymentNotFoundException(String message) {
        super(message);
    }

    public PaymentNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public PaymentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
