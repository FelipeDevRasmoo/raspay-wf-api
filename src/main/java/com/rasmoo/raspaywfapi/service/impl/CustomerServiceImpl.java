package com.rasmoo.raspaywfapi.service.impl;

import com.rasmoo.raspaywfapi.dto.CustomerDto;
import com.rasmoo.raspaywfapi.mapper.CustomerMapper;
import com.rasmoo.raspaywfapi.model.Customer;
import com.rasmoo.raspaywfapi.repository.CustomerRepository;
import com.rasmoo.raspaywfapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<Customer> findById(String id) {
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Customer not found")));
    }

    @Override
    public Mono<Customer> create(CustomerDto dto) {
        return customerRepository.findByEmail(dto.email())
                .flatMap(customer -> {
                    customer.setFirstName(dto.firstName());
                    customer.setLastName(dto.lastName());
                    return customerRepository.save(customer);
                }).switchIfEmpty(customerRepository.save(mapper.toModel(dto)));
    }

    @Override
    public Flux<Customer> findAll(String firstName, String email, String cpf, int pageNumber, int pageSize, String sort) {
        Criteria criteria = new Criteria();

        if (!Objects.equals(firstName,"")) {
            criteria.and("firstName").regex("^"+firstName,"i");
        }

        if (!Objects.equals(email,"")) {
            criteria.and("email").regex(".*"+email+".*", "i");
        }

        if (!Objects.equals(cpf,"")) {
            criteria.and("cpf").is(cpf);
        }
        Pageable pageable =
                PageRequest.of(pageNumber,pageSize, Sort.Direction.valueOf(sort.toUpperCase()), "firstName");

        Query query = new Query().with(pageable);
        query.addCriteria(criteria);
        return mongoTemplate.find(query, Customer.class);
    }
}
