package com.rasmoo.raspaywfapi.repository;

import com.rasmoo.raspaywfapi.model.CreditCard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends MongoRepository<CreditCard, String> {
}
