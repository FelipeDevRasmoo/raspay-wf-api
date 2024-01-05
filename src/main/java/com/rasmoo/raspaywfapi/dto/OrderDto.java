package com.rasmoo.raspaywfapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record OrderDto(

        @NotBlank
        String customerId,
        @Min(0)
        BigDecimal discount,

        @NotBlank
        String productAcronym

) {}
