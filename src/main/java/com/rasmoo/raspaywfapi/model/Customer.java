package com.rasmoo.raspaywfapi.model;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("customers")
public class Customer {

    @Id
    private String id;

    @Email
    @Indexed(unique = true)
    private String email;

    private String firstName;

    private String lastName;

    @CPF
    @Indexed(unique = true)
    private String cpf;
}
