package com.rasmoo.raspaywfapi.repository;

import com.rasmoo.raspaywfapi.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
