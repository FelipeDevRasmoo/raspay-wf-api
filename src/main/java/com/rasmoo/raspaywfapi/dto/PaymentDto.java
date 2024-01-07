package com.rasmoo.raspaywfapi.dto;


public record PaymentDto(

        CreditCardDto creditCard,
        String customerId,
        String orderId


) {
}
