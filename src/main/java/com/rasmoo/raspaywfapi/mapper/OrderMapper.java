package com.rasmoo.raspaywfapi.mapper;

import com.rasmoo.raspaywfapi.dto.OrderDto;
import com.rasmoo.raspaywfapi.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "originalPrice", ignore = true)
    @Mapping(target = "dtRegistedOrder", ignore = true)
    @Mapping(target = "productId", ignore = true)
    Order toModel(OrderDto dto);
}
