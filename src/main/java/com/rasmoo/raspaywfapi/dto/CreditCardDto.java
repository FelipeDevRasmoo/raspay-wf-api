package com.rasmoo.raspaywfapi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record CreditCardDto(
        @Size(min = 3, max = 3)
        String cvv,

        @CPF
        String documentNumber,

        @Min(1)
        @Max(12)
        int month,

        @Size(min = 16, max = 16)
        String number,

        @Min(24)
        @Max(40)
        int year,

        @Min(0)
        @Max(12)
        int installments


) {
}
