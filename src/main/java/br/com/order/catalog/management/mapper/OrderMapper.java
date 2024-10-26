package br.com.order.catalog.management.mapper;

import br.com.order.catalog.management.dto.OrderDTO;
import br.com.order.catalog.management.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDTO toDTO(Order order);

    Order toEntity(OrderDTO orderDTO);
    
}