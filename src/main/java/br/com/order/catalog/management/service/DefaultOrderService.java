package br.com.order.catalog.management.service;

import br.com.order.catalog.management.domain.order.Order;
import br.com.order.catalog.management.domain.order.OrderItem;
import br.com.order.catalog.management.domain.order.PreOrder;
import br.com.order.catalog.management.entity.OrderItemJpaEntity;
import br.com.order.catalog.management.exceptions.ResourceNotFoundException;
import br.com.order.catalog.management.mapper.OrderMapper;
import br.com.order.catalog.management.repository.OrderItemRepository;
import br.com.order.catalog.management.repository.OrderRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultOrderService implements OrderService {

  private final static String PRODUCT_NOT_FOUND_ERROR_MESSAGE = "Product not found";

  private final OrderRepository orderRepository;

  private final OrderItemRepository orderItemRepository;

  private final OrderMapper orderMapper;

  private final ProductService productService;

  public DefaultOrderService(OrderRepository orderRepository,
      OrderItemRepository orderItemRepository, OrderMapper orderMapper,
      ProductService productService) {
    this.orderRepository = orderRepository;
    this.orderItemRepository = orderItemRepository;
    this.orderMapper = orderMapper;
    this.productService = productService;
  }

  public Order getOrderById(UUID id) {
    final var orderEntityJpa = orderRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND_ERROR_MESSAGE));

    return orderMapper.toDomain(orderEntityJpa);
  }

  public Page<Order> getOrders(Pageable pageable) {
    final var pageOrderEntity = orderRepository.findAll(pageable);
    return pageOrderEntity.map(orderMapper::toDomain);
  }

  @Transactional
  public Order createOrder(PreOrder preOrder) {
    final var orderItems = getOrderItems(preOrder);

    final var order = Order.newOrder(preOrder.status(), orderItems, preOrder.discount());

    final var orderEntity = orderMapper.toEntity(order);
    return orderMapper.toDomain(orderRepository.save(orderEntity));
  }

  @Transactional
  public Order updateOrder(UUID id, PreOrder preOrder) {

    final var orderItems = getOrderItems(preOrder);

    final var existingOrderEntity = orderRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND_ERROR_MESSAGE));

    orderItemRepository.deleteAll(existingOrderEntity.getItems());

    existingOrderEntity.setItems(new ArrayList<>(orderItems.stream()
        .map(orderItem -> OrderItemJpaEntity.from(orderItem, existingOrderEntity)).toList()));
    existingOrderEntity.setStatus(preOrder.status());
    existingOrderEntity.setDiscount(preOrder.discount());

    final var mergedOrder = orderRepository.save(existingOrderEntity);
    return orderMapper.toDomain(mergedOrder);
  }

  private HashSet<OrderItem> getOrderItems(PreOrder preOrder) {
    // FIXME - PONTO DE FALHA
    final var products = productService.getProductsById(preOrder.getItemIds());
    final var orderItems = new HashSet<OrderItem>();
    products.forEach(product -> orderItems.add(
        OrderItem.newOrderItem(product, preOrder.getItemAmountByItemId(product.getId()))));
    return orderItems;
  }

  @Transactional
  public void deleteOrderById(UUID id) {
    if (!orderRepository.existsById(id)) {
      throw new ResourceNotFoundException(PRODUCT_NOT_FOUND_ERROR_MESSAGE);
    }
    orderRepository.deleteById(id);
  }
}
