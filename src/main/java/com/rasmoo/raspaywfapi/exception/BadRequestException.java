package com.rasmoo.raspaywfapi.exception;

public class BadRequestException extends RuntimeException{
    BadRequestException(final String message) {
        super(message);
    }
}
