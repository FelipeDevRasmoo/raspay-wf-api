package com.rasmoo.raspaywfapi.dto;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

public record CustomerDto(
        String firstName,

        String lastName,

        @Email String email,

        @CPF String cpf
) {
}
