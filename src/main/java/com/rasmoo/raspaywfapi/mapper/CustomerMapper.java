package com.rasmoo.raspaywfapi.mapper;

import com.rasmoo.raspaywfapi.dto.CustomerDto;
import com.rasmoo.raspaywfapi.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface CustomerMapper {
    @Mapping(target = "id", ignore = true)
    Customer toModel(CustomerDto customerDto);
}
