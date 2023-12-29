package com.rasmoo.raspaywfapi.exception;

public class NotFoundException extends RuntimeException{
    NotFoundException(final String message) {
        super(message);
    }
}
