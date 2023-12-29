package com.rasmoo.raspaywfapi.exception.handler;

import com.rasmoo.raspaywfapi.dto.exception.ErrorResponseDto;
import com.rasmoo.raspaywfapi.exception.BadRequestException;
import com.rasmoo.raspaywfapi.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ResourceHandler {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Mono<ErrorResponseDto>> notFoundException(NotFoundException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Mono.just(errorResponseDto));

    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Mono<ErrorResponseDto>> notFoundException(BadRequestException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Mono.just(errorResponseDto));

    }
}
