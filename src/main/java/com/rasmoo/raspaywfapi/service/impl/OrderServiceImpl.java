package com.rasmoo.raspaywfapi.service.impl;

import com.rasmoo.raspaywfapi.dto.OrderDto;
import com.rasmoo.raspaywfapi.exception.BadRequestException;
import com.rasmoo.raspaywfapi.exception.NotFoundException;
import com.rasmoo.raspaywfapi.mapper.OrderMapper;
import com.rasmoo.raspaywfapi.model.Order;
import com.rasmoo.raspaywfapi.repository.OrderRepository;
import com.rasmoo.raspaywfapi.service.CustomerService;
import com.rasmoo.raspaywfapi.service.OrderService;
import com.rasmoo.raspaywfapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper mapper;
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    @Override
    public Mono<Order> create(OrderDto orderDto) {
        return customerService.findById(orderDto.customerId())
                .flatMap(customer -> productService.findByAcronym(orderDto.productAcronym())
                .flatMap(product -> {
                    Order order = mapper.toModel(orderDto);
                    if (orderDto.discount().intValue() > 0) {
                        if (orderDto.discount().compareTo(product.getCurrentPrice()) > 0) {
                            return Mono.error(() -> new BadRequestException("Discount can not be greater than currentPrice"));
                        }
                        order.setOriginalPrice(product.getCurrentPrice().subtract(orderDto.discount()));
                    }
                    order.setProduct(product);
                    order.setCustomer(customer);
                    return orderRepository.save(order);
                }));
    }

    @Override
    public Mono<Order> findById(String id) {
        return orderRepository.findById(id)
                .switchIfEmpty(Mono.error(() -> new NotFoundException("Order not found")));
    }
}
