package com.rasmoo.raspaywfapi.dto.exception;

public record ErrorResponseDto(
        String message,
        int httpStatusCode
) {
}
