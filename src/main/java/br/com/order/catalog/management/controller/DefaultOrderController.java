package br.com.order.catalog.management.controller;

import br.com.order.catalog.management.dto.OrderDTO;
import br.com.order.catalog.management.service.OrderService;

import org.springframework.http.ResponseEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.UUID;


public class DefaultOrderController implements OrderController {

    private final OrderService orderService;

    public DefaultOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public EntityModel<OrderDTO> findById(UUID id) {
        OrderDTO orderDTO = orderService.getOrderById(id);
        EntityModel<OrderDTO> resource = EntityModel.of(orderDTO);

        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).findById(id)).withSelfRel();
        resource.add(selfLink);

        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).findAll(null)).withRel("orders"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).update(id, orderDTO)).withRel("update"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).deleteById(id)).withRel("delete"));

        return resource;
    }

    public Page<OrderDTO> findAll(Pageable pageable) {
        return orderService.getOrders(pageable);
    }

    public EntityModel<OrderDTO> create(OrderDTO orderToBeSaved) {
        OrderDTO savedOrder = orderService.saveOrder(orderToBeSaved);
        EntityModel<OrderDTO> resource = EntityModel.of(savedOrder);

        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).create(orderToBeSaved)).withSelfRel();
        resource.add(selfLink);

        return resource;
    }

    public EntityModel<OrderDTO> update(UUID id, OrderDTO updatedOrder) {
        OrderDTO updatedOrderDTO = orderService.updateOrder(id, updatedOrder);
        EntityModel<OrderDTO> resource = EntityModel.of(updatedOrderDTO);

        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).update(id, updatedOrder)).withSelfRel();
        resource.add(selfLink);

        return resource;
    }

    public ResponseEntity<Void> deleteById(UUID id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }
}
