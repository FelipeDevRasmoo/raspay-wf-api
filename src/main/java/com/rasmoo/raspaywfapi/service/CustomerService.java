package com.rasmoo.raspaywfapi.service;

import com.rasmoo.raspaywfapi.dto.CustomerDto;
import com.rasmoo.raspaywfapi.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Mono<Customer> findById(String id);

    Mono<Customer> create(CustomerDto dto);

    Flux<Customer> findAll(String firstName, String email, String cpf, int pageNumber, int pageSize, String sort);
}
