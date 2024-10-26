package br.com.order.catalog.management.controller;

import br.com.order.catalog.management.dto.OrderDTO;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.hateoas.EntityModel;

import jakarta.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public interface OrderController {

  @GetMapping("/{id}")
    public EntityModel<OrderDTO> findById(@PathVariable("id") UUID id);

    @GetMapping
    public Page<OrderDTO> findAll(Pageable pageable);

    @PostMapping
    public EntityModel<OrderDTO> create(@Valid @RequestBody OrderDTO orderToBeSaved);

    @PutMapping("/{id}")
    public EntityModel<OrderDTO> update(@PathVariable("id") UUID id, @Valid @RequestBody OrderDTO updatedOrder);

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id);

}
