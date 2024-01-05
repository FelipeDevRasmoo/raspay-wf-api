package com.rasmoo.raspaywfapi.service.impl;

import com.rasmoo.raspaywfapi.dto.OrderDto;
import com.rasmoo.raspaywfapi.exception.NotFoundException;
import com.rasmoo.raspaywfapi.model.Order;
import com.rasmoo.raspaywfapi.repository.OrderRepository;
import com.rasmoo.raspaywfapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Mono<Order> create(OrderDto dto) {
        return null;
    }

    @Override
    public Mono<Order> findById(String id) {
        return orderRepository.findById(id)
                .switchIfEmpty(Mono.error(() -> new NotFoundException("Order not found")));
    }
}
