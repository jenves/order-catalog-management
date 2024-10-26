package br.com.order.catalog.management.controller;

import br.com.order.catalog.management.dto.OrderDTO;
import br.com.order.catalog.management.service.DefaultProductService;
import br.com.order.catalog.management.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import jakarta.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/{id}")
    public EntityModel<OrderDTO> findById(@PathVariable("id") UUID id) {
        OrderDTO orderDTO = orderService.getOrderById(id);
        EntityModel<OrderDTO> resource = EntityModel.of(orderDTO);

        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).findById(id)).withSelfRel();
        resource.add(selfLink);

        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).findAll(null)).withRel("orders"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).update(id, orderDTO)).withRel("update"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).deleteById(id)).withRel("delete"));

        return resource;
    }

    @GetMapping
    public Page<OrderDTO> findAll(Pageable pageable) {
        return orderService.getOrders(pageable);
    }

    @PostMapping
    public EntityModel<OrderDTO> create(@Valid @RequestBody OrderDTO orderToBeSaved) {

        OrderDTO savedOrder = orderService.saveOrder(orderToBeSaved);

        EntityModel<OrderDTO> resource = EntityModel.of(savedOrder);

        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).create(orderToBeSaved)).withSelfRel();
        resource.add(selfLink);

        return resource;
    }

    @PutMapping("/{id}")
    public EntityModel<OrderDTO> update(@PathVariable("id") UUID id, @Valid @RequestBody OrderDTO updatedOrder) {
        OrderDTO updatedOrderDTO = orderService.updateOrder(id, updatedOrder);
        EntityModel<OrderDTO> resource = EntityModel.of(updatedOrderDTO);

        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).update(id, updatedOrder)).withSelfRel();
        resource.add(selfLink);

        return resource;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }
}
