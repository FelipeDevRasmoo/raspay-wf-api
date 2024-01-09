package com.rasmoo.raspaywfapi.mapper;

import com.rasmoo.raspaywfapi.dto.CreditCardDto;
import com.rasmoo.raspaywfapi.model.CreditCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface CreditCardMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customerId", ignore = true)
    CreditCard toModel(CreditCardDto dto);
}
