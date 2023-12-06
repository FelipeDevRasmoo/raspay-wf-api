package com.rasmoo.raspaywfapi.repository;

import com.rasmoo.raspaywfapi.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
}
