package com.rasmoo.raspaywfapi.repository;

import com.rasmoo.raspaywfapi.model.Product;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    Mono<Product> findByAcronym(String acronym);

    @Query("{name:{$regex:'^?0', $options:'i'}}")
    Flux<Product> findAllByName(@Param("name") String name);
}
