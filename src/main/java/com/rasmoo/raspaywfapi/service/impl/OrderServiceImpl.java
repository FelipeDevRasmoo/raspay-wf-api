package com.rasmoo.raspaywfapi.service.impl;

import com.rasmoo.raspaywfapi.dto.OrderDto;
import com.rasmoo.raspaywfapi.exception.BadRequestException;
import com.rasmoo.raspaywfapi.exception.NotFoundException;
import com.rasmoo.raspaywfapi.mapper.OrderMapper;
import com.rasmoo.raspaywfapi.model.Customer;
import com.rasmoo.raspaywfapi.model.Order;
import com.rasmoo.raspaywfapi.model.Product;
import com.rasmoo.raspaywfapi.repository.OrderRepository;
import com.rasmoo.raspaywfapi.service.CustomerService;
import com.rasmoo.raspaywfapi.service.OrderService;
import com.rasmoo.raspaywfapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper mapper;
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    @Override
    public Mono<Order> create(OrderDto orderDto) {
        Mono<Customer> customerMono = customerService.findById(orderDto.customerId());
       Mono<Product> productMono =  productService.findByAcronym(orderDto.productAcronym());
        return Mono.zip(customerMono, productMono)
                .flatMap(tuple -> {
                    Customer customer = tuple.getT1();
                    Product product = tuple.getT2();
                    Order order = mapper.toModel(orderDto);
                    if (orderDto.discount().intValue() > 0) {
                        if (orderDto.discount()
                                .compareTo(product.getCurrentPrice()) > 0) {
                            return Mono.error(() ->
                                    new BadRequestException("Discount can not be greater than currentPrice"));
                        }
                        order.setOriginalPrice(product.getCurrentPrice().subtract(orderDto.discount()));
                    } else {
                        order.setOriginalPrice(product.getCurrentPrice());
                    }
                    order.setDtRegistedOrder(LocalDateTime.now());
                    order.setProductId(product.getId());
                    order.setCustomerId(customer.getId());
                    return orderRepository.save(order);
                });
    }

    @Override
    public Mono<Order> findById(String id) {
        return orderRepository.findById(id)
                .switchIfEmpty(Mono.error(() -> new NotFoundException("Order not found")));
    }
}
