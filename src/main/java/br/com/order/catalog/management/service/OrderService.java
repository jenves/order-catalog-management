package br.com.order.catalog.management.service;

import br.com.order.catalog.management.dto.OrderDTO;
import br.com.order.catalog.management.entity.Order;
import br.com.order.catalog.management.exceptions.ResourceNotFoundException;
import br.com.order.catalog.management.mapper.OrderMapper;
import br.com.order.catalog.management.repository.OrderRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    public OrderDTO getOrderById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

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
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        existingOrder.setItens(orderToBeChanged.getItens());
        existingOrder.setStatus(orderToBeChanged.getStatus());
        existingOrder.setDiscount(orderToBeChanged.getDiscount());

        Order updatedOrder = orderRepository.save(existingOrder);
        return orderMapper.toDTO(updatedOrder);
    }

    public void deleteOrderById(UUID id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found");
        }
        orderRepository.deleteById(id);
    }
}
