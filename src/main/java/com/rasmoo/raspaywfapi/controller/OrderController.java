package com.rasmoo.raspaywfapi.controller;

import com.rasmoo.raspaywfapi.dto.OrderDto;
import com.rasmoo.raspaywfapi.model.Order;
import com.rasmoo.raspaywfapi.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Mono<Order>> create(@Valid  @RequestBody OrderDto orderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(orderDto));
    }

}
