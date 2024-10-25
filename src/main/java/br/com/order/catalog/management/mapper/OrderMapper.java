package br.com.order.catalog.management.mapper;

import br.com.order.catalog.management.dto.OrderDTO;
import br.com.order.catalog.management.entity.Order;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    @Autowired
    private ModelMapper modelMapper;

    public OrderDTO toDTO(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }

    public Order toEntity(OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, Order.class);
    }
}
