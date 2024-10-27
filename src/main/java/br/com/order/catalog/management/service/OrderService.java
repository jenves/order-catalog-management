package br.com.order.catalog.management.service;

import br.com.order.catalog.management.domain.order.Order;

import br.com.order.catalog.management.domain.order.PreOrder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderService {

  Order getOrderById(UUID id);

  Page<Order> getOrders(Pageable pageable);

  Order createOrder(PreOrder preOrder);

  Order updateOrder(UUID id, PreOrder preOrder);

  void deleteOrderById(UUID id);
}
