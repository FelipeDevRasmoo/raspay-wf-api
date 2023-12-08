package com.rasmoo.raspaywfapi.service.impl;

import com.rasmoo.raspaywfapi.dto.CustomerDto;
import com.rasmoo.raspaywfapi.mapper.CustomerMapper;
import com.rasmoo.raspaywfapi.model.Customer;
import com.rasmoo.raspaywfapi.repository.CustomerRepository;
import com.rasmoo.raspaywfapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;
    @Override
    public Mono<Customer> create(CustomerDto dto) {
        return customerRepository.save(mapper.toModel(dto));
    }
}
