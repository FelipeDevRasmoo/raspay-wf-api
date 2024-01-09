package com.rasmoo.raspaywfapi.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("credit-cards")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreditCard {

    @Id
    private String id;

    @NotBlank
    private String number;

    private String cvv;

    private int year;

    private String documentNumber;

    private int installments;

    private String customerId;
}
