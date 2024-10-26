package br.com.order.catalog.management.service;

import br.com.order.catalog.management.dto.OrderDTO;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderService {

  OrderDTO getOrderById(UUID id);

  Page<OrderDTO> getOrders(Pageable pageable);

  OrderDTO saveOrder(@Valid OrderDTO orderToBeSaved);

  OrderDTO updateOrder(UUID id, @Valid OrderDTO orderToBeChanged);

  void deleteOrderById(UUID id);
}
