package com.rasmoo.raspaywfapi.service.impl;

import com.rasmoo.raspaywfapi.dto.PaymentDto;
import com.rasmoo.raspaywfapi.emums.PaymentStatus;
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
                     .flatMap(creditCard -> {
                         var paymentBuilder = Payment.builder();
                         paymentBuilder
                                 .dtRegistedPayment(LocalDateTime.now())
                                 .order(order)
                                 .customer(customer)
                                 .creditCard(creditCard);
                         if (creditCard.getCustomer().getId().equals(customer.getId())
                         || creditCard.getDocumentNumber().equals(customer.getCpf())) {
                             paymentBuilder.status(PaymentStatus.APPROVED);
                         } else {
                             paymentBuilder.status(PaymentStatus.DISAPPROVED);
                         }
                         return paymentRepository.save(paymentBuilder.build());

                     }).onErrorResume(error->
                         creditCardService.create(paymentDto.creditCard(), customer)
                                 .flatMap(creditCard -> {
                                     var paymentBuilder = Payment.builder();
                                     paymentBuilder
                                             .dtRegistedPayment(LocalDateTime.now())
                                             .order(order)
                                             .customer(customer)
                                             .creditCard(creditCard);
                                     return paymentRepository.save(paymentBuilder.build());
                                 })
                     )
        ).flatMap(paymentMono -> paymentMono);
    }
}
