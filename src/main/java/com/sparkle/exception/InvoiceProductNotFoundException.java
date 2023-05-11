package com.sparkle.exception;

public class InvoiceProductNotFoundException extends RuntimeException{

    public InvoiceProductNotFoundException(String message){
        super(message);
    }
}
