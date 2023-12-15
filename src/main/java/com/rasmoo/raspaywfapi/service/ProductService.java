package com.rasmoo.raspaywfapi.service;

import com.rasmoo.raspaywfapi.dto.ProductDto;
import com.rasmoo.raspaywfapi.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<Product> create(ProductDto dto);

    Flux<Product> findAll();

    Mono<Product> findByAcronym(String acronym);

    Flux<Product> findAllByName(String name, int pageNumber, int pageSize);

    Flux<Product> findAllByParams(String acronym, String name, String currentPrice, int pageNumber, int pageSize);
}
