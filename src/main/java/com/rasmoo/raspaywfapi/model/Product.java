package com.rasmoo.raspaywfapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document("products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private String id;

    @Indexed(unique = true)
    @Size(min = 6,max = 20)
    private String acronym;

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal currentPrice;

    private LocalDate dtCreation = LocalDate.now();
}
