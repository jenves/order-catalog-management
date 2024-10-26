package br.com.order.catalog.management.controller.order.model;

import br.com.order.catalog.management.domain.order.OrderItem;
import br.com.order.catalog.management.domain.order.OrderStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import java.util.UUID;

public record OrderResponse(

        UUID id,
        @NotNull(message = "Status is required") OrderStatus status,

        List<OrderItem> items,
        @Positive(message = "Discount must be positive") Double discount
) { }