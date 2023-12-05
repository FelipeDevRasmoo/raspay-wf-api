package com.rasmoo.raspaywfapi.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("creditCards")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreditCard {

    @Id
    private String id;

    @NotBlank
    private String number;

    private Long cvv;

    private Long year;

    private String documentNumber;

    private long installments;

    @DBRef
    private Customer customer;
}
