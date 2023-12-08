package com.rasmoo.raspaywfapi.dto;

import org.hibernate.validator.constraints.br.CPF;

public record CustomerDto (
    String firstName,

    String lastName,

    String email,

    @CPF String cpf
){}
