package com.rasmoo.raspaywfapi.service.impl;

import com.rasmoo.raspaywfapi.dto.ProductDto;
import com.rasmoo.raspaywfapi.mapper.ProductMapper;
import com.rasmoo.raspaywfapi.model.Product;
import com.rasmoo.raspaywfapi.repository.ProductRepository;
import com.rasmoo.raspaywfapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final ReactiveMongoTemplate mongoTemplate;
    @Override
    public Mono<Product> create(ProductDto dto) {
        return productRepository.save(productMapper.toModel(dto));
    }

    @Override
    public Flux<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Mono<Product> findByAcronym(String acronym) {
        return productRepository.findByAcronym(acronym);
    }

    @Override
    public Flux<Product> findAllByName(String name) {
        return productRepository.findAllByName(name);
    }

    @Override
    public Flux<Product> findAllByParams(String acronym, String name, String currentPrice) {

        Criteria criteria = new Criteria();

        if (!Objects.equals(acronym,"")){
            criteria.and("acronym").is(acronym);
        }

        if (!Objects.equals(name,"")){
            criteria.and("name").regex("^"+name, "i");
        }

        if (!Objects.equals(currentPrice,"")){
            criteria.and("currentPrice").lte(currentPrice);
        }

        Query query = new Query();
        query.addCriteria(criteria);

        return mongoTemplate.find(query, Product.class);
    }
}
