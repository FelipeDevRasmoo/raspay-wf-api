package com.rasmoo.raspaywfapi.controller;

import com.rasmoo.raspaywfapi.dto.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/v1/product")
@RestController
public class ProductController {

    @PostMapping
    public ResponseEntity<Mono<Void>> create(@RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping
    public ResponseEntity<Flux<ProductDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


}
