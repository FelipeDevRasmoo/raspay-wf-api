package com.rasmoo.raspaywfapi.dto;

import java.math.BigDecimal;

public record OrderDto(

        String customerId,
        BigDecimal discount,
        String productAcronym

) {}
