package com.rasmoo.raspaywfapi.mapper;

import com.rasmoo.raspaywfapi.dto.ProductDto;
import com.rasmoo.raspaywfapi.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dtCreation", ignore = true)
    Product toModel(ProductDto dto);
}
