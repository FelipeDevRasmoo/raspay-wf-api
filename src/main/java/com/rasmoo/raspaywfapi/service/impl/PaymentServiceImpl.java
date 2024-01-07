package com.rasmoo.raspaywfapi.service.impl;

import com.rasmoo.raspaywfapi.dto.PaymentDto;
import com.rasmoo.raspaywfapi.emums.PaymentStatus;
import com.rasmoo.raspaywfapi.model.CreditCard;
import com.rasmoo.raspaywfapi.model.Customer;
import com.rasmoo.raspaywfapi.model.Order;
import com.rasmoo.raspaywfapi.model.Payment;
import com.rasmoo.raspaywfapi.repository.PaymentRepository;
import com.rasmoo.raspaywfapi.service.CreditCardService;
import com.rasmoo.raspaywfapi.service.CustomerService;
import com.rasmoo.raspaywfapi.service.OrderService;
import com.rasmoo.raspaywfapi.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final CustomerService customerService;

    private final OrderService orderService;

    private final CreditCardService creditCardService;

    private final PaymentRepository paymentRepository;

    @Override
    public Mono<Payment> process(PaymentDto paymentDto) {
        Mono<Customer> customerMono = customerService.findById(paymentDto.customerId());
        Mono<Order> orderMono = orderService.findById(paymentDto.orderId());
        return Mono.zip(customerMono, orderMono, (customer, order) ->
                creditCardService.findByNumber(paymentDto.creditCard().number())
                        .flatMap(creditCard ->
                                authorizePayment(customer, order, creditCard)
                        ).onErrorResume(error ->
                                authorizePaymentWithNewCreditCard(paymentDto, customer, order)
                        )
        ).flatMap(paymentMono -> paymentMono);
    }

    private Mono<Payment> authorizePaymentWithNewCreditCard(PaymentDto paymentDto, Customer customer, Order order) {
        return creditCardService.create(paymentDto.creditCard(), customer)
                .flatMap(creditCard ->
                        savePayment(customer, order, creditCard, PaymentStatus.APPROVED)
                );
    }

    private Mono<Payment> authorizePayment(Customer customer, Order order, CreditCard creditCard) {
        if (creditCard.getCustomer().getId().equals(customer.getId())
                || creditCard.getDocumentNumber().equals(customer.getCpf())) {
            return savePayment(customer, order, creditCard, PaymentStatus.APPROVED);
        } else {
            return savePayment(customer, order, creditCard, PaymentStatus.DISAPPROVED);
        }
    }

    private Mono<Payment> savePayment(Customer customer, Order order, CreditCard creditCard, PaymentStatus status) {
        var paymentBuilder = Payment.builder();
        paymentBuilder
                .dtRegistedPayment(LocalDateTime.now())
                .order(order)
                .customer(customer)
                .creditCard(creditCard)
                .status(status);
        return paymentRepository.save(paymentBuilder.build());
    }
}
