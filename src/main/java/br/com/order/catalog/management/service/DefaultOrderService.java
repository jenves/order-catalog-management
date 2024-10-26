package br.com.order.catalog.management.service;

import br.com.order.catalog.management.dto.OrderDTO;
import br.com.order.catalog.management.entity.Order;
import br.com.order.catalog.management.exceptions.ResourceNotFoundException;
import br.com.order.catalog.management.mapper.OrderMapper;
import br.com.order.catalog.management.repository.OrderRepository;

import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

@Service
public class DefaultOrderService implements OrderService {

  private final static String PRODUCT_NOT_FOUND_ERROR_MESSAGE = "Product not found";

  private final OrderRepository orderRepository;

  private final OrderMapper orderMapper;

  public DefaultOrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
    this.orderRepository = orderRepository;
    this.orderMapper = orderMapper;
  }

  public OrderDTO getOrderById(UUID id) {
    Order order = orderRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND_ERROR_MESSAGE));

    return orderMapper.toDTO(order);
  }

  public Page<OrderDTO> getOrders(Pageable pageable) {
    Page<Order> ordersPage = orderRepository.findAll(pageable);
    return ordersPage.map(orderMapper::toDTO);
  }

  public OrderDTO saveOrder(@Valid OrderDTO orderToBeSaved) {
    Order order = orderMapper.toEntity(orderToBeSaved);
    return orderMapper.toDTO(orderRepository.save(order));
  }

  public OrderDTO updateOrder(UUID id, @Valid OrderDTO orderToBeChanged) {
    Order existingOrder = orderRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND_ERROR_MESSAGE));

    existingOrder.setItens(orderToBeChanged.items());
    existingOrder.setStatus(orderToBeChanged.status());
    existingOrder.setDiscount(orderToBeChanged.discount());

    Order updatedOrder = orderRepository.save(existingOrder);
    return orderMapper.toDTO(updatedOrder);
  }

  public void deleteOrderById(UUID id) {
    if (!orderRepository.existsById(id)) {
      throw new ResourceNotFoundException(PRODUCT_NOT_FOUND_ERROR_MESSAGE);
    }
    orderRepository.deleteById(id);
  }
}
