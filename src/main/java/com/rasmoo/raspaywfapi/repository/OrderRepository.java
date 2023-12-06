package com.rasmoo.raspaywfapi.repository;

import com.rasmoo.raspaywfapi.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
}
