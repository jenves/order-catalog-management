package br.com.order.catalog.management.dto;

import br.com.order.catalog.management.entity.OrderJpaEntity;
import br.com.order.catalog.management.entity.ProductJpaEntity;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public class OrderItemDTO {

    private UUID id;

    private OrderJpaEntity orderJpaEntity;

    private ProductJpaEntity productJpaEntity;

    @Positive(message = "Amount must be positive")
    private int amount;

}