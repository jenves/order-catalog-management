package br.com.order.catalog.management.dto;

import br.com.order.catalog.management.entity.Order;
import br.com.order.catalog.management.entity.Product;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public class OrderItemDTO {

    private UUID id;

    private Order order;

    private Product product;

    @Positive(message = "Amount must be positive")
    private int amount;

}
