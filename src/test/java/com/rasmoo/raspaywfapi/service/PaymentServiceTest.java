package com.rasmoo.raspaywfapi.service;

import com.rasmoo.raspaywfapi.dto.CreditCardDto;
import com.rasmoo.raspaywfapi.dto.PaymentDto;
import com.rasmoo.raspaywfapi.emums.PaymentStatus;
import com.rasmoo.raspaywfapi.model.CreditCard;
import com.rasmoo.raspaywfapi.model.Customer;
import com.rasmoo.raspaywfapi.model.Order;
import com.rasmoo.raspaywfapi.model.Payment;
import com.rasmoo.raspaywfapi.repository.PaymentRepository;
import com.rasmoo.raspaywfapi.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private OrderService orderService;

    @Mock
    private CreditCardService creditCardService;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;


    @Test
    void shouldApprovePaymentWhenCreditCardIsFoundAndCustomerIdIsCorrect() {
        Customer customer = getCustomer();
        Order order = getOrder(customer.getId());
        CreditCardDto creditCardDto = new CreditCardDto(
                "123",
                customer.getCpf(),
                12,
                "0123456789012345",
                28,
                1
        );

        PaymentDto paymentDto = new PaymentDto(
                creditCardDto,
                customer.getId(),
                order.getId()
        );

        when(customerService.findById(paymentDto.customerId()))
                .thenReturn(Mono.just(customer));

        when(orderService.findById(paymentDto.orderId()))
                .thenReturn(Mono.just(order));

        CreditCard creditCard = new CreditCard();
        creditCard.setId("123456");
        creditCard.setDocumentNumber("74949192019");
        creditCard.setCustomerId(customer.getId());

        when(creditCardService.findByNumber(creditCardDto.number()))
                .thenReturn(Mono.just(creditCard));

        Payment paymentSaved = new Payment();
        paymentSaved.setStatus(PaymentStatus.APPROVED);

        when(paymentRepository.save(any())).thenReturn(Mono.just(paymentSaved));

        Mono<Payment> paymentMono = paymentService.process(paymentDto);

        StepVerifier.create(paymentMono)
                .expectNextMatches(payment -> payment.getStatus().equals(PaymentStatus.APPROVED))
                .verifyComplete();

        Mockito.verify(customerService, times(1)).findById(paymentDto.customerId());
        Mockito.verify(orderService, times(1)).findById(paymentDto.orderId());
        Mockito.verify(creditCardService, times(1)).findByNumber(paymentDto.creditCard().number());
        Mockito.verify(creditCardService, times(0)).create(any(),any());
        Mockito.verify(paymentRepository, times(1)).save(any());
    }

    private Customer getCustomer() {
        Customer customer = new Customer();
        customer.setId("123456");
        customer.setCpf("40750233036");
        return customer;
    }

    private Order getOrder(String customerId) {
        Order order = new Order();
        order.setId("123456");
        order.setCustomerId("123456");
        return order;
    }
}
