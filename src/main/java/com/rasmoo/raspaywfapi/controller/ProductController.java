package com.rasmoo.raspaywfapi.controller;

import com.rasmoo.raspaywfapi.dto.ProductDto;
import com.rasmoo.raspaywfapi.model.Product;
import com.rasmoo.raspaywfapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/v1/product")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Mono<Void>> create(@RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(productDto).then());
    }

    @GetMapping
    public ResponseEntity<Flux<Product>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());
    }

    @GetMapping("acronym/{acronym}")
    public ResponseEntity<Mono<Product>> findByAcronym(@PathVariable("acronym") String acronym) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findByAcronym(acronym));
    }

    @GetMapping("name/{name}")
    public ResponseEntity<Flux<Product>> findAllByName(
            @PathVariable("name") String name,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAllByName(name, pageNumber, pageSize));
    }

    @GetMapping("params")
    public ResponseEntity<Flux<Product>> findAllByParams(
            @RequestParam(value = "acronym", required = false, defaultValue = "") String acronym,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "currentPrice", required = false, defaultValue = "") String currentPrice,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize
            ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAllByParams(acronym,name,currentPrice,  pageNumber,  pageSize));
    }

}
