package com.rasmoo.raspaywfapi.service;

import com.rasmoo.raspaywfapi.dto.CustomerDto;
import com.rasmoo.raspaywfapi.model.Customer;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Mono<Customer> create(CustomerDto dto);
}
