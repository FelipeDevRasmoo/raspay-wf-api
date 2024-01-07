package com.rasmoo.raspaywfapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("payments")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Payment {

    @Id
    private String id;

    private String status;

    private LocalDateTime dtRegistedPayment;

    @DBRef
    private CreditCard creditCard;

    @DBRef
    private Order order;

    @DBRef
    private Customer customer;
}
