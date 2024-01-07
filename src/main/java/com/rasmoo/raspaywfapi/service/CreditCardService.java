package com.rasmoo.raspaywfapi.service;

import com.rasmoo.raspaywfapi.dto.CreditCardDto;
import com.rasmoo.raspaywfapi.model.CreditCard;
import com.rasmoo.raspaywfapi.model.Customer;
import reactor.core.publisher.Mono;

public interface CreditCardService {

    Mono<CreditCard> findByNumber(String number);

    Mono<CreditCard> create(CreditCardDto creditCardDto, Customer customer);

}
